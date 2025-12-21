package com.lbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            //  Create loader with FXML
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/mainView.fxml")
            );

            //  Create scene
            Scene scene = new Scene(loader.load());

            //  Attach CSS
            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
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
