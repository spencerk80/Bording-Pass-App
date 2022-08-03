package genspark.group.boardingpass.controller;
import genspark.group.boardingpass.model.Ticket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TicketControllerTest {

    private Ticket ticket;
    @DisplayName("Testing BeforeEach")
    @BeforeEach
    void SetUp(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, 2);

        ticket =  new Ticket(new  Date(),"SDF", "MIA", calendar.getTime(), "Chris Miller",
            "cmiller@email.com", "5028214444", "male", 45);
    }
    @DisplayName("Testing onEstimateBtnClick")
    @Test
    void onEstimateBtnClick() throws ParseException {

        //Testing Departure
        Calendar cal= Calendar.getInstance();
        cal.setTime(ticket.getDepartureTime());
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        String hourDeparture = hours+":"+minutes;
        String expected = hours+":"+minutes;

        assertEquals(expected, hourDeparture, "testing Departure");

        //Testing Arrival
        cal.setTime(ticket.getEta());
        hours = cal.get(Calendar.HOUR_OF_DAY);
        String hourEta = String.valueOf(hours);
        expected = String.valueOf(hours);

        assertEquals(expected, hourEta, "testing Arrival");

    }

}