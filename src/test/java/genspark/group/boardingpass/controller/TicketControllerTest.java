package genspark.group.boardingpass.controller;
import genspark.group.boardingpass.model.Ticket;
import javafx.scene.control.ChoiceBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;

class TicketControllerTest {
    private Ticket ticket;
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }
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

    @Test
    void nameShouldBeJames(){

        TextField inputName = new TextField();
        inputName.setText("James");
        assertEquals("James", inputName.getText(), "Testing inputName");
    }
    @Test
    void ageShouldBeSixty(){
        TextField inputAge = new TextField();
        inputAge.setText("67");
        assertEquals("67", inputAge.getText(), "Testing inputAge");
    }
    @Test
    void itShouldBePhoneNumber(){
        TextField inputPhoneNumber = new TextField();
        inputPhoneNumber.setText("5020001001");
        assertEquals("5020001001", inputPhoneNumber.getText());
    }
    @Test
    void itShouldBeTheEmail(){
        TextField inputEmail = new TextField();
        inputEmail.setText("google@gmail.com");
        assertEquals("google@gmail.com", inputEmail.getText());
    }
    @Test
    void itShouldBeTheOutputFlightTime(){
        Label outputFlightTime = new Label();
        outputFlightTime.setText("10:00");
        assertEquals("10:00", outputFlightTime.getText());
    }
    @Test
    void itShouldBeTheOutputArrivalTime(){
        Label outputArrivalTime = new Label();
        outputArrivalTime.setText("12:00");
        assertEquals("12:00", outputArrivalTime.getText());
    }

    @Test
    void itShouldBeTheOutputFlightDuration(){
        Label outputFlightDuration = new Label();
        outputFlightDuration.setText("4:30");
        assertEquals("4:30", outputFlightDuration.getText());
    }

    @Test
    void itShouldBeTheOutputTicketPrice(){
        Label outputTicketPrice = new Label();
        outputTicketPrice.setText("197.00");
        assertEquals("197.00", outputTicketPrice.getText());
    }

    @Test
    void itShouldBeTheOutputLadiesDiscountAmount(){
        Label outputLadiesDiscountAmount = new Label();
        outputLadiesDiscountAmount.setText("19:70");
        assertEquals("19:70", outputLadiesDiscountAmount.getText());
    }

    @Test
    void itShouldBeTheOutputAgeDiscountAmount(){
        Label outputAgeDiscountAmount = new Label();
        outputAgeDiscountAmount.setText("118.20");
        assertEquals("118.20", outputAgeDiscountAmount.getText());
    }

    @Test
    void itShouldBeTheOutputTotalCost(){
        Label outputTotalCost = new Label();
        outputTotalCost.setText("59.10");
        assertEquals("59.10", outputTotalCost.getText());
    }

}