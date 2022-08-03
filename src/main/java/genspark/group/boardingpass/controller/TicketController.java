package genspark.group.boardingpass.controller;
import genspark.group.boardingpass.dao.TicketDao;
import genspark.group.boardingpass.model.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    TicketController(){}

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
            //Departure
            Calendar cal= Calendar.getInstance();
            cal.setTime(ticket.getDepartureTime());
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int minutes = cal.get(Calendar.MINUTE);
            String hourDeparture = hours+":"+minutes;
            outputFlightTime.setText(hourDeparture);

            //Arrival
            cal.setTime(ticket.getEta());
            hours = cal.get(Calendar.HOUR_OF_DAY);
            minutes = cal.get(Calendar.MINUTE);
            String hourEta = hours+":"+minutes;
            outputArrivalTime.setText(hourEta);

            //Flight Time
            outputFlightDuration.setText(String.valueOf(ticket.getFlightTime()));

            //Ticket price
            outputTicketPrice.setText("$ "+ ticket.getTicketPrice());

            //Gender Discount
            outputLadiesDiscountAmount.setText("$ "+ ticket.getGenderDiscount());

            //Age discount
            outputAgeDiscountAmount.setText("$ "+ ticket.getAgeDiscount());

            //Total Cost
            outputTotalCost.setText("$ "+ ticket.getTicketPrice());
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
}
