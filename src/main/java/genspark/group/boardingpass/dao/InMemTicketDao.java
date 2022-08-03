package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/* Author: Kristoffer Spencer
 *
 * This implementation of the TicketDao interface is meant for testing. It stores the tickets in an ArrayList in
 * ram. This is NOT meant for production!
 *
 * Tickets are lost on app restart.
 */
public class InMemTicketDao implements TicketDao {
    private static InMemTicketDao  instance; //Singleton instance
    private List<Ticket> tickets;

    private InMemTicketDao() {
        tickets = new ArrayList<>();
    }

    //Returns the singleton instance of this dao impl class
    public static InMemTicketDao getInstance() {
        if(instance == null) instance = new InMemTicketDao();
        return instance;
    }

    //Simple. Add the ticket to the list if it's not in there already
    @Override
    public void writeTicket(Ticket ticket) throws IOException {
        if(tickets.contains(ticket)) return;
        tickets.add(ticket);
    }

    //Look for the ticket and return it if it exists
    @Override
    public Ticket readTicket(UUID ticketID) throws IOException {
        AtomicReference<Ticket> ticket = new AtomicReference<>();

        tickets.forEach(t -> {
            if(t.getBoardingPassNumber().equals(ticketID)) ticket.set(t);
        });

        return ticket.get();
    }

    /* Find the ticket if it exists and replace it with the passed in ticket. I.e over-write it.
     * The below line may look confusing, but it's saving a new list of a mapping of the old where the ticket, if
     * not equal the updated ticket ID, is just kept. If it is equal to the passed in ticket ID, then we save the
     * passed in ticket in place of the old one.
     */
    @Override
    public void updateTicket(Ticket ticket) throws IOException {
        //If t (of for each ticket) == passed in ticket (the ids match) save the new ticket (fields may be diff)
        tickets = tickets.stream().map(t -> t.equals(ticket) ? ticket : t).collect(Collectors.toList());
    }
}
