package gentspark.group.boardingpass.dao;

import genspark.group.boardingpass.model.Ticket;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
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
    void getBoardingPass() {
        var ticket= makeTicket();
        assertNotNull(ticket.getBoardingPassNumber());
        System.out.println(ticket.getBoardingPassNumber());
    }

    @Test
    void generatePrice() {
        var ticket= makeTicket();
        double price= ticket.getTotalPrice();
        System.out.println("Price : "+ price);
        assertAll("Testing multiple price ranges : ",
                () -> assertNotEquals(0, price),
                () -> assertTrue(price>= 60&& price<= 960)
                );
    }

    @Test
    void generateEta() {
        var ticket= makeTicket();
        assertNotEquals(new Date(), ticket.getEta());
        System.out.println("Estimated Arrival : "+ ticket.getEta());
    }

    @Test
    void workingDate() {
        var ticket= makeTicket();
        Date ticketDate= ticket.getDate();
        assertNotNull(ticketDate);
        System.out.println("Creates Ticket with current date : "+ ticketDate);
    }

    @Test
    void currentYear() {
        SimpleDateFormat getYear= new SimpleDateFormat("yyyy");
        var ticket= makeTicket();

        String ticketYear= getYear.format(ticket.getDate());

        Date date= new Date();
        String currentYear= getYear.format(date);

        assertEquals(currentYear, ticketYear);
        System.out.println("Current Year : "+ currentYear+ "\nTicket Year : "+ ticketYear);
    }

    @Test
    void flightTime() {
        var ticket= makeTicket();
        int flightHrs= ticket.getFlightTime();
        System.out.println("Flight Time : "+ flightHrs);
        assertAll("Testing flight hours : ",
                () -> assertNotNull(flightHrs),
                () -> assertNotEquals(0, flightHrs),
                () -> assertTrue(flightHrs>= 3&& flightHrs<= 12)
                );
    }

}
