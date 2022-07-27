package genspark.group.boardingpass.dao;

import java.util.UUID;

public interface TicketDao {
    static TicketDao dao = null;

    void writeTicket(Ticket ticket);
    Ticket readTicket(UUID ticketID);
    void updateTicket(Ticket ticket);
}
