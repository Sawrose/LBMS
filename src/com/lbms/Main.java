package com.lbms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage stage) {
        Parent root = null;

        // Load FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/auth/LoginView.fxml"));
            if (loader.getLocation() == null) {
                throw new NullPointerException("FXML file not found: /fxml/auth/LoginView.fxml");
            }
            root = loader.load();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException while loading FXML", e);
            return;
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "FXML file is missing", e);
            return;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown exception while loading FXML", e);
            return;
        }

        Scene scene = new Scene(root);

        // Stage settings
        try {
            stage.setTitle("Library Management System");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IllegalStateException e) {
            logger.log(Level.SEVERE, "IllegalStateException while showing stage", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unknown exception while setting up stage", e);
        }
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception in launch()", e);
        }
    }
}
