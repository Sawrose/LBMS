package com.lbms.controller.librarian;

import com.lbms.dao.UserDAO;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddLibrarianFormController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField roleField;

    @FXML
    private Button saveBtnAddLibrarian;

    @FXML
    private Button cancelBtnAddLibrarian;

    @FXML
    private Label AddLibMsgLabel;

    @FXML
    private void initialize() {

    }

    public void CancelAddLibForm(ActionEvent actionEvent) {
        closeWindow();

    }

    @FXML
    public void SaveLibrarianSaveBtn(ActionEvent actionEvent) {
        saveLibrarian();
    }

    private void saveLibrarian() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = "librarian";

        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.insertUserLibrarian(username, password, role);

        if (success) {
            AddLibMsgLabel.setText("✔ Librarian Added Successfully");
            AddLibMsgLabel.setStyle("-fx-text-fill: green;");

            // Pause for 2 seconds, then close
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> closeWindow());
            pause.play();

        } else {
            AddLibMsgLabel.setText("✖ Failed to Add Librarian");
            AddLibMsgLabel.setStyle("-fx-text-fill: red;");
        }
    }





    // Close the window
    private void closeWindow() {
        Stage stage = (Stage) saveBtnAddLibrarian.getScene().getWindow();
        stage.close();
    }


}
