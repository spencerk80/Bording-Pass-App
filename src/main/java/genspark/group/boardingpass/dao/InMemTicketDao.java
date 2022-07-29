package genspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class InMemTicketDao implements TicketDao {
    private static InMemTicketDao  instance;
    private List<Ticket> tickets;

    private InMemTicketDao() {
        tickets = new ArrayList<>();
    }

    public static InMemTicketDao getInstance() {
        if(instance == null) instance = new InMemTicketDao();
        return instance;
    }

    @Override
    public void writeTicket(Ticket ticket) throws IOException {
        if(tickets.contains(ticket)) return;
        tickets.add(ticket);
    }

    @Override
    public Ticket readTicket(UUID ticketID) throws IOException {
        AtomicReference<Ticket> ticket = new AtomicReference<>();

        tickets.forEach(t -> {
            if(t.getBoardingPassNumber().equals(ticketID)) ticket.set(t);
        });

        return ticket.get();
    }

    @Override
    public void updateTicket(Ticket ticket) throws IOException {
        //If t (of for each ticket) == passed in ticket (the ids match) save the new ticket (fields may be diff)
        tickets = tickets.stream().map(t -> t.equals(ticket) ? ticket : t).collect(Collectors.toList());
    }
}
