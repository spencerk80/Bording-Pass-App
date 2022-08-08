package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/* Author: Kristoffer Spencer
 *
 * This is an implementation of the TicketDao interface that will read and write the tickets to files. Originally
 * I wanted to write to a single file using RandomAccessFile, but it doesn't write objects, and I realized this is
 * mostly attributed to that data fields won't be the same size between discrete objects.
 *
 * Instead, this implementation writes a ticket to its own file inside a folder of tickets. The file name will
 * include the ticket UUID, ensuring the file name is unique.
 */
public class FileTicketDao implements TicketDao {
    private static final String         TICKET_SAVE_FILE    = "tickets/%s.dat";
    private static       FileTicketDao  instance; //Singleton instance

    //Return the singleton instance of this dao
    public static FileTicketDao getInstance() {
        if(instance == null) instance = new FileTicketDao();
        return instance;
    }

    //This method is used to create the file name. It's set to public so that the file name can be accessed else-where
    //such as in tests
    public static String getTicketFileName(UUID id) {
        return String.format(TICKET_SAVE_FILE, id.toString());
    }

    //Very simple method. Take the ticket and use an ObjectOutputStream to write it to a discrete file
    @Override
    public void writeTicket(Ticket ticket) throws IOException {
        Path                saveDir     = Paths.get(TICKET_SAVE_FILE.substring(0, TICKET_SAVE_FILE.indexOf('/')));
        File                saveFile    = new File(getTicketFileName(ticket.getBoardingPassNumber()));
        ObjectOutputStream  writer;

        //Check for, and create if-not-exist, the tickets dir
        if( !Files.exists(saveDir)) Files.createDirectory(saveDir);

        writer = new ObjectOutputStream(new FileOutputStream(saveFile));
        writer.writeObject(ticket);
        writer.close();
    }

    //Much like the write method, this method is very simple and just uses an ObjectInputStream to read the object
    //from file
    @Override
    public Ticket readTicket(UUID ticketID) throws IOException {
        Path                saveDir     = Paths.get(TICKET_SAVE_FILE.substring(0, TICKET_SAVE_FILE.indexOf('/')));
        File                saveFile    = new File(getTicketFileName(ticketID));
        ObjectInputStream   reader;
        Ticket ticket;

        //Check for and create if-not-exist the tickets dir
        if( ! Files.exists(saveDir)) Files.createDirectory(saveDir);

        try {
            reader = new ObjectInputStream(new FileInputStream(saveFile));
            ticket = (Ticket) reader.readObject();
            reader.close();
        } catch(ClassNotFoundException e) {
            throw new IOException(e.getMessage());
        } catch(FileNotFoundException e) {
            return null;
        }

        return ticket;
    }

    //In this implementation, to update the ticket save is to over-write the file. Thus it's the same as calling the
    //write method. However, the interface specifies a discrete method for updating because a SQL, in-mem, etc
    //implementation would require different steps to update the ticket. See InMemTicketDao for example
    @Override
    public void updateTicket(Ticket ticket) throws IOException {
        writeTicket(ticket);
    }
}
