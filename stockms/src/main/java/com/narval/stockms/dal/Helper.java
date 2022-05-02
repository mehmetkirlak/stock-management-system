package com.narval.stockms.dal;

import javafx.scene.control.Alert;

public class Helper {

    private Helper(){}

    public static void confirmation(String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setContentText(title);
        alert.showAndWait();
    }
    public static void error(String title){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setContentText(title);
        alert.showAndWait();
    }

}
