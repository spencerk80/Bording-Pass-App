package genspark.group.boardingpass.controller;
import genspark.group.boardingpass.Ticket;
import genspark.group.boardingpass.dao.TicketDao;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TicketController {

    @FXML
    TextField inputName;
    @FXML
    TextField inputAge;
    @FXML
    ChoiceBox inputGenderSelection;
    @FXML
    TextField inputPhoneNumber;
    @FXML
    TextField inputEmail;
    @FXML
    DatePicker inputDate;
    @FXML
    ChoiceBox inputOriginSelection;
    @FXML
    ChoiceBox inputDestinationSelection;
    @FXML
    ChoiceBox inputDepartTime;

    @FXML
    Label outputFlightTime;

    @FXML
    Label outputArrivalTime;

    @FXML
    Label outputFlightDuration;

    @FXML
    Label outputTicketPrice;

    @FXML
    Label outputLadiesDiscountAmount;

    @FXML
    Label outputAgeDiscountAmount;

    @FXML
    Label outputTotalCost;

    @FXML
    Button bookFlight_btn;

    Ticket ticket;

    TicketController(){

        bookFlight_btn.setEnabled(false);

    }


    public void onEstimateBtnClick() throws ParseException {

        if (inputDate.getValue() != null && inputOriginSelection.getValue() != null &&
                inputDestinationSelection.getValue() != null && inputDepartTime.getValue() != null &&
                inputName.getText() != null && inputEmail.getText() != null && inputAge.getText() != null &&
                inputPhoneNumber.getText() != null && inputGenderSelection.getValue() != null){

            //Enable bookFlight_btn
            bookFlight_btn.setEnabled(true);

            Date flightDate = new SimpleDateFormat("MM/dd/yyyy").parse(inputDate.getValue().toString());

            String origin = inputOriginSelection.getValue().toString();
            String destination = inputDestinationSelection.getValue().toString();

            String inputDepartTMP = inputDepartTime.getValue().toString();
            int index =  inputDepartTMP.indexOf(":");
            String str = inputDate.getValue().toString() + " "+inputDepartTMP.substring(0, index)+":"+inputDepartTMP.substring(index+1);
            Date departTime = new SimpleDateFormat("MM/dd/yyyy hh:mm").parse(str);

            String name = inputName.getText();
            String email = inputEmail.getText();
            String phone = inputPhoneNumber.getText();
            String gender = inputGenderSelection.getValue().toString();

            int age = 0;
            try{
                age = Integer.parseInt(inputAge.getText());
            }catch (Exception e){
                e.printStackTrace();
            }

            //create a new instance of Ticket
            Ticket ticket = new Ticket(flightDate, origin, destination, departTime, name, email, phone, gender, age);


            //print Flight Details Panel
            outputFlightTime.setText(ticket.getDepartureTime().toString());

            //check where values are coming
            outputArrivalTime.setText("eta"); //eta exists in Ticket, but I am not able to get its value"
            outputFlightDuration.setText("5:45"); //"should be the difference between before times"

            //Ticket price
            outputTicketPrice.setText("$ " + "00.00"); //"Total Price"
            outputLadiesDiscountAmount.setText("$ " + "00.00" ); //"Gender discount"
            outputAgeDiscountAmount.setText("$ " + "00.00"); //"Age discount"
            outputTotalCost.setText("$ " + "00.00"); //"Price - discounts"

        }

    }

    public void onBookBtnClick(){

        //write a ticket
        try{
            writeTicket(ticket);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeTicket(Ticket ticket) throws IOException {
        TicketDao.dao.writeTicket(ticket);
    }

    public Ticket readTicket (UUID ticketID) throws IOException {
        return TicketDao.dao.readTicket(ticketID);
    }

    public void updateTicket(Ticket ticket) throws IOException {
        TicketDao.dao.updateTicket(ticket);
    }

}
