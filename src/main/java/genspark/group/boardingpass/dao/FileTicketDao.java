package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.Ticket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileTicketDao implements TicketDao {
    private static final String         TICKET_SAVE_FILE    = "tickets/%s.dat";
    private static       FileTicketDao  instance;

    public static FileTicketDao getInstance() {
        if(instance == null) instance = new FileTicketDao();
        return instance;
    }

    public static String getTicketFileName(UUID id) {
        return String.format(TICKET_SAVE_FILE, id.toString());
    }

    @Override
    public void writeTicket(Ticket ticket) throws IOException {
        Path                saveDir     = Paths.get(TICKET_SAVE_FILE.substring(0, TICKET_SAVE_FILE.indexOf('/')));
        File                saveFile    = new File(getTicketFileName(ticket.getBoardingPassNumber()));
        ObjectOutputStream  writer;

        if( !Files.exists(saveDir)) Files.createDirectory(saveDir);

        writer = new ObjectOutputStream(new FileOutputStream(saveFile));
        writer.writeObject(ticket);
        writer.close();
    }

    @Override
    public Ticket readTicket(UUID ticketID) throws IOException {
        Path                saveDir     = Paths.get(TICKET_SAVE_FILE.substring(0, TICKET_SAVE_FILE.indexOf('/')));
        File                saveFile    = new File(getTicketFileName(ticketID));
        ObjectInputStream   reader;
        Ticket ticket;

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

    @Override
    public void updateTicket(Ticket ticket) throws IOException {
        writeTicket(ticket);
    }
}
