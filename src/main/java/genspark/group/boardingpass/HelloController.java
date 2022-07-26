package genspark.group.boardingpass;

import genspark.group.boardingpass.data.BoardingPass;
import genspark.group.boardingpass.data.FlightDetails;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalTime;


public class HelloController {
    public Label outputFlightTime;
    public Label outputArrivalTime;
    public Label outputFlightDuration;
    public Label outputTicketPrice;
    public Label outputLadiesDiscountAmount;
    public Label outputAgeDiscountAmount;
    public Label outputTotalCost;
    public Button bookFlight_btn;
    private BoardingPass boardingPassData;
    private FlightDetails flightDetails;

    @FXML
    private TextField inputName;
    @FXML
    private TextField inputAge;
    @FXML
    private ChoiceBox<String> inputGenderSelection;
    @FXML
    private TextField inputPhoneNumber;
    @FXML
    private TextField inputEmail;
    @FXML
    private DatePicker inputDate;
    @FXML
    private ChoiceBox<String> inputOriginSelection;
    @FXML
    private ChoiceBox<String> inputDestinationSelection;
    @FXML
    private ChoiceBox<LocalTime> inputDepartTime;
    @FXML
    private Button estimate_btn;


    public void initialize() {
        boardingPassData = new BoardingPass();
        flightDetails = new FlightDetails();
        inputGenderSelection.getItems().addAll("Male", "Female");
        inputOriginSelection.getItems().addAll("ATL");
        inputDestinationSelection.getItems().addAll("LAX","ORD","DFW","DEN","JFK","SFO","LAS","SEA","CLT","MCO","MIA","PHX","EWR","IAH");
        inputDepartTime.getItems().addAll(LocalTime.of(6, 30), LocalTime.of(8, 35), LocalTime.of(11, 10), LocalTime.of(14, 30), LocalTime.of(20, 50));
        inputAge.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputAge.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        inputPhoneNumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputPhoneNumber.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
