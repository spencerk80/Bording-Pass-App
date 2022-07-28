package gentspark.group.boardingpass.dao;

import genspark.group.boardingpass.Ticket;
import genspark.group.boardingpass.dao.TicketDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TicketDaoTest {

    //Shortcut. Object is already static singleton. No chance of mem-leak by this
    private TicketDao dao = TicketDao.dao;

    private Ticket makeTicket() {
        return new Ticket(new Date(),"Origin", "destination", new Date(), "Bob Smith",
                "b.smith@email.com", "5555555555", "male", 32);
    }

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
            assertSame(foundTicket, ticket);
        } catch(IOException e) {
            fail();
        }
    }

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
            fail();
        }
    }

}
