module genspark.group.boardingpass {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    opens genspark.group.boardingpass.dao to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
    opens genspark.group.boardingpass.model to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
    opens genspark.group.boardingpass.controller to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
    opens genspark.group.boardingpass to javafx.graphics, javafx.fxml, javafx.base, javafx.controls;
    exports genspark.group.boardingpass.model;
    exports genspark.group.boardingpass.dao;
    exports genspark.group.boardingpass.controller;
    exports genspark.group.boardingpass;
}
