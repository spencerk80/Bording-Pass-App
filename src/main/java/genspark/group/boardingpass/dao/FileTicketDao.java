package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.Ticket;

import java.io.IOException;
import java.util.UUID;

public class FileTicketDao implements TicketDao {
    public static final String  TICKET_SAVE_FILE    = "tickets.dat";
    @Override
    public void writeTicket(Ticket ticket) throws IOException {

    }

    @Override
    public Ticket readTicket(UUID ticketID) throws IOException {
        return null;
    }

    @Override
    public void updateTicket(Ticket ticket) throws IOException {

    }
}
