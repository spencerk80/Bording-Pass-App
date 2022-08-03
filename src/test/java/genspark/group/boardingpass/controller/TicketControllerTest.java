package genspark.group.boardingpass.controller;
import genspark.group.boardingpass.model.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TicketControllerTest {

    @FXML
    TextField inputName;
    @FXML
    TextField inputAge;
    @FXML
    TextField inputPhoneNumber;
    @FXML
    TextField inputEmail;
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

        // Testing Nodes
        inputName.setText("Bryan Miller");
        inputAge.setText("30");
        inputPhoneNumber.setText("5023220101");
        inputEmail.setText("bmiller@gmail.com");

        assertEquals("Bryan Miller", inputName.getName());
        assertEquals("30", inputAge.getText());
        assertEquals("5023220101", inputPhoneNumber.getText());
        assertEquals("bmiller@gmail.com", inputEmail.getText());

        //Testing Departure
        Calendar cal= Calendar.getInstance();
        cal.setTime(ticket.getDepartureTime());
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        String hourDeparture = hours+":"+minutes;

        assertEquals("20:39", hourDeparture, "testing Departure");

        //Testing Arrival
        cal.setTime(ticket.getEta());
        hours = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        String hourEta = hours+":"+minutes;

        assertEquals("7:39", hourEta, "testing Arrival");

    }

}