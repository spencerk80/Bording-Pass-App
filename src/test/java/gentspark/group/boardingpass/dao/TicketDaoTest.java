package gentspark.group.boardingpass.dao;

import genspark.group.boardingpass.dao.TicketDao;
import genspark.group.boardingpass.model.Ticket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/* Author: Kristoffer Spencer
 *
 * This is a test set to test the dao for read/write accuracy
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketDaoTest {

    //Shortcut. Object is already static singleton. No chance of mem-leak by this
    private final TicketDao dao = TicketDao.dao;

    //Helper method to make a ticket more easily when needed
    private Ticket makeTicket() {
        return new Ticket(new Date(),"Origin", "destination", new Date(), "Bob Smith",
                "b.smith@email.com", "5555555555", "male", 32);
    }

    //This tests writes a ticket and reads it back to compare the state of the ticket before and after
    @Test
    public void writeAndReadTicket() {
        Ticket  ticket          = makeTicket(),
                foundTicket;

        try {
            //Should return null
            foundTicket = dao.readTicket(makeTicket().getBoardingPassNumber());
            assertNull(foundTicket);

            //Writing and reading the ticket back out should result in the same ticket
            dao.writeTicket(ticket);
            foundTicket = dao.readTicket(ticket.getBoardingPassNumber());
            assertEquals(foundTicket, ticket);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            fail();
        }
    }

    //This test writes multiple tickets and then tries to read one from the middle of tha group and compares. This is
    //to make sure it can read a specific ticket and doesn't get the wrong one
    @Test
    public void writeManyReadOne() {
        Ticket[]    tickets = new Ticket[5];
        Ticket      foundTicket;

        for(byte i = 0; i < 5; i++) tickets[i] = makeTicket();

        //Write 5 tickets and read the 4th (index 3) ticket
        try {
            for(byte i = 0; i < 5; i++) dao.writeTicket(tickets[i]);

            foundTicket = dao.readTicket(tickets[3].getBoardingPassNumber());
            assertEquals(foundTicket, tickets[3]);
        } catch(IOException e) {
            System.err.println(e.getMessage());
            fail();
        }
    }

    //This test makes sure a ticket's information can be updated and the changes are saved and read back
    @Test
    public void updateTicket() {
        Ticket  ticket              = makeTicket(),
                returnedTicket;

        try {
            //Save it
            dao.writeTicket(ticket);

            //Change it. Original value was 32
            ticket.setAge(42);
            dao.updateTicket(ticket);

            //Read it back out and compare. Make sure update happened
            returnedTicket = dao.readTicket(ticket.getBoardingPassNumber());
            assertNotEquals(32, returnedTicket.getAge());
            assertEquals(ticket.getAge(), returnedTicket.getAge());
            assertEquals(42, returnedTicket.getAge());
        } catch(IOException e) {
            System.err.println(e.getMessage());
            fail();
        }
    }

}
