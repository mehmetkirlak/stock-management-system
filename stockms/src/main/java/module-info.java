module com.narval.stockms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;


    opens com.narval.stockms to javafx.fxml;
    exports com.narval.stockms;
    opens com.narval.stockms.controller to javafx.fxml;
    exports com.narval.stockms.controller;
    opens com.narval.stockms.model to javafx.fxml;
    exports com.narval.stockms.model;

}