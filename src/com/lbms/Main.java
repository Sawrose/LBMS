package com.lbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            //  Create loader with FXML
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/auth/LoginView.fxml")
            );

            //  Create scene
            Scene scene = new Scene(loader.load());

            scene.getStylesheets().addAll(
                    Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm(),
                    Objects.requireNonNull(getClass().getResource("/css/rightPaneButton.css")).toExternalForm(),
                    Objects.requireNonNull(getClass().getResource("/css/LeftPaneButton.css")).toExternalForm()
            );

            //  Stage settings
            stage.setTitle("Library Management System");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace(); // shows exact error in console
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
