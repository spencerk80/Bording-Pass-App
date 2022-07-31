package genspark.group.boardingpass.controller;

import genspark.group.boardingpass.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketControllerTest {

    private Ticket ticket;


    @DisplayName("Testing BeforeEach")
    @BeforeEach
    void SetUp(){
    ticket =  new Ticket(new  Date(),"SDF", "MIA", new Date(), "Chris Miller",
            "cmiller@email.com", "5028214444", "male", 45);
    }

    @DisplayName("Testing onEstimateBtnClick")
    @Test
    void onEstimateBtnClick() {

        assertEquals(ticket.getOrigin(), "SDF");
        assertEquals(ticket.getDestination(), "MIA");
        assertEquals(ticket.getName(), "Chris Miller");
        assertEquals(ticket.getGender(), "male");
        assertEquals(ticket.getAge(), 45);
    }

}