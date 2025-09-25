module example.speedw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens example.speedw to javafx.fxml;
    exports example.speedw;
    exports example.speedw.controllers;
    opens example.speedw.controllers to javafx.fxml;
}