package com.narval.stockms.controller;

import com.narval.stockms.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


import java.io.IOException;

public class LoginController {
    @FXML
    TextField fld_username;
    @FXML
    PasswordField fld_password;
    String username = "sa";
    String password = "123";

    public LoginController() {
    }

    @FXML
    public void login() throws IOException {
        Main main = new Main();
        Alert alert;
        if (!this.fld_username.getText().equals(this.username) && !this.fld_password.getText().equals(this.password)) {
            if (this.fld_username.getLength() != 0 && this.fld_password.getLength() != 0) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Yanlış Kullanıcı adı/Şifre. ");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle(null);
                alert.setContentText("Lütfen alanları doldurunuz.");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setContentText("Başarıyla giriş yapıldı.");
            alert.showAndWait();

            main.changeScene("/main.fxml");

        }

    }
}
