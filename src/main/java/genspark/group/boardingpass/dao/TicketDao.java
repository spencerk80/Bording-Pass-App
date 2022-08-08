package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;

import java.io.IOException;
import java.util.UUID;

/* Author: Kristoffer Spencer
 *
 * This interface specifies the behavior of the daos. Most conveniently, it allows the one-line switch of
 * implementations. For example, at first there was only the InMemTicketDao, which allowed for testing and others to
 * use a dao while the more complex logic of the FileTicketDao was being worked on. Once it was ready, the below
 * declaration of dao was changed from InMemTicketDao.getInstance() to FileticketDao.getInstance() as seen below.
 */
public interface TicketDao {
    //Allows code to access the selected dao via TicketDao.dao. This allows a one-line switch of dao implementations
    TicketDao dao = FileTicketDao.getInstance();

    void writeTicket(Ticket ticket) throws IOException;
    Ticket readTicket(UUID ticketID) throws IOException;
    void updateTicket(Ticket ticket) throws IOException;
}