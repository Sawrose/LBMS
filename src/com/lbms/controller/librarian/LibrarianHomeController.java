package com.lbms.controller.librarian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianHomeController {

    @FXML
    private Button addLibrarianBtn;

    @FXML
    private void initialize() {
        addLibrarianBtn.setOnAction(e -> openAddLibrarianForm());
    }

    private void openAddLibrarianForm() {
        try {
            // Load the FXML for the form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/librarian/AddLibrarianForm.fxml"));
            AnchorPane pane = loader.load();

            // Create a new stage for the form
            Stage stage = new Stage();
            stage.setTitle("Add Librarian");
            stage.setScene(new Scene(pane));

            // Make it modal to block interaction with the dashboard
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);


            stage.centerOnScreen();
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
