package gentspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TicketTest {

    private Ticket makeTicket() {
        Date date= new Date();
        return new Ticket(
                date, "NY", "ATL",
                date, "Jane Doe", "JaneDoe@gmail.com",
                "555-555-5555", "female", 23
        );
    }

    @Test
    void generatePrice() {
        var ticket= makeTicket();
        assertNotEquals(0, ticket.getPrice());
        System.out.println("Price : "+ ticket.getPrice());
    }

    @Test
    void generateEta() {
        var ticket= makeTicket();
        assertNotEquals(new Date(), ticket.getEta());
        System.out.println("Estimated Arrival : "+ ticket.getEta());
    }

    @Test
    void flightTime() {
        var ticket= makeTicket();
        assertNotEquals(0, ticket.getFlightTime());
        System.out.println("Flight Time : "+ ticket.getFlightTime());
    }

}
