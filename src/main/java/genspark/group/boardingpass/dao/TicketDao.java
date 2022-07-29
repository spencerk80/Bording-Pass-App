package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.Ticket;

import java.io.IOException;
import java.util.UUID;

public interface TicketDao {
    TicketDao dao = InMemTicketDao.getInstance();

    void writeTicket(Ticket ticket) throws IOException;
    Ticket readTicket(UUID ticketID) throws IOException;
    void updateTicket(Ticket ticket) throws IOException;
}