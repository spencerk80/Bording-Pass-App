module genspark.group.boardingpass {
    requires javafx.controls;
    requires javafx.fxml;


    opens genspark.group.boardingpass to javafx.fxml;
    exports genspark.group.boardingpass;
}