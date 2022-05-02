
package com.narval.stockms;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stg;

    public void start(Stage stage) throws Exception {
        stg = stage;
        stg.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        stage.setTitle("Stock Management System");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setScene(new Scene(pane));

    }

    public static void main(String[] args) {
        launch(args);
    }
}
