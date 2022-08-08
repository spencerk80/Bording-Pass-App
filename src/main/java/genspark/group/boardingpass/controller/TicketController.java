package genspark.group.boardingpass.controller;

import genspark.group.boardingpass.dao.TicketDao;
import genspark.group.boardingpass.model.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TicketController {
    @FXML
    private TextField inputName;
    @FXML
    private TextField inputAge;
    @FXML
    private ChoiceBox inputGenderSelection;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    private TextField inputEmail;
    @FXML
    private DatePicker inputDate;
    @FXML
    private ChoiceBox inputOriginSelection;
    @FXML
    private ChoiceBox inputDestinationSelection;
    @FXML
    private ChoiceBox inputDepartTime;
    public Label outputFlightTime;
    public Label outputArrivalTime;
    public Label outputFlightDuration;
    public Label outputTicketPrice;
    public Label outputLadiesDiscountAmount;
    public Label outputAgeDiscountAmount;
    public Label outputTotalCost;
    public Button bookFlight_btn;
    public Button estimate_btn;

    Ticket ticket;

    public TicketController(){}

    public void onEstimateBtnClick() throws ParseException {

        if (inputDate.getValue() != null && inputOriginSelection.getValue() != null &&
                inputDestinationSelection.getValue() != null && inputDepartTime.getValue() != null &&
                inputName.getText() != null && inputEmail.getText() != null && inputAge.getText() != null &&
                inputPhoneNumber.getText() != null && inputGenderSelection.getValue() != null){

            //Enable bookFlight_btn
            bookFlight_btn.setDisable(false);

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
