package com.lbms.controller.librarian;

import com.lbms.dao.UserDAO;
import com.lbms.validation.LibInputValidation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddLibrarianFormController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField PwVisibleField; // optional visible password

    @FXML
    private TextField PhnField;

    @FXML
    private Label AddLibMsgLabel;

    @FXML
    private Button saveBtnAddLibrarian, cancelBtnAddLibrarian;

    // Password rules labels
    @FXML
    private Label lengthLabel, lowercaseLabel, uppercaseLabel, digitLabel, specialCharLabel;

    @FXML
    private Pane passwordRulesContainer;

    private boolean isPasswordVisible = false;

    @FXML
    public void initialize() {
        setupPhoneField();
        setupPasswordRules();
    }

    // ---------------- Phone Field: +977 Auto-Prepend ----------------
    private void setupPhoneField() {
        if (PhnField.getText() == null || PhnField.getText().isEmpty()) {
            PhnField.setText("+977");
        }

        PhnField.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.startsWith("+977")) {
                PhnField.setText("+977");
            }
            if (newText.length() > 4) {
                String rest = newText.substring(4).replaceAll("[^0-9]", "");
                PhnField.setText("+977" + rest);
            }
        });

        // Move caret to end on click
        PhnField.setOnMouseClicked(event -> PhnField.positionCaret(PhnField.getText().length()));
    }

    // ---------------- Password Rules Logic ----------------
    private void setupPasswordRules() {
        passwordRulesContainer.setVisible(false);
        passwordRulesContainer.setManaged(false);

        // Show pane on focus
        passwordField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (newFocus) {
                passwordRulesContainer.setVisible(true);
                passwordRulesContainer.setManaged(true);
            }
        });

        // Real-time typing
        passwordField.textProperty().addListener((obs, oldText, newText) -> checkPasswordRules(newText));
    }

    private void checkPasswordRules(String password) {
        LibInputValidation.PasswordRulesResult rules = LibInputValidation.checkPasswordRules(password);

        // Reset all to red first
        lengthLabel.setStyle("-fx-text-fill: red;");
        lowercaseLabel.setStyle("-fx-text-fill: red;");
        uppercaseLabel.setStyle("-fx-text-fill: red;");
        digitLabel.setStyle("-fx-text-fill: red;");
        specialCharLabel.setStyle("-fx-text-fill: red;");

        // Turn green if satisfied
        if (rules.length) lengthLabel.setStyle("-fx-text-fill: green;");
        if (rules.lowercase) lowercaseLabel.setStyle("-fx-text-fill: green;");
        if (rules.uppercase) uppercaseLabel.setStyle("-fx-text-fill: green;");
        if (rules.digit) digitLabel.setStyle("-fx-text-fill: green;");
        if (rules.specialChar) specialCharLabel.setStyle("-fx-text-fill: green;");

        // Hide panel if all rules met
        passwordRulesContainer.setVisible(!rules.allOk());
        passwordRulesContainer.setManaged(!rules.allOk());
    }

    // ---------------- Button Handlers ----------------
    @FXML
    private void CancelAddLibForm(ActionEvent event) {
        closeWindow();
    }

    @FXML
    private void SaveLibrarianSaveBtn(ActionEvent event) {
        saveLibrarian();
    }

    private void saveLibrarian() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String phone = PhnField.getText().trim();

        // Validate inputs
        String validationResult = LibInputValidation.validate(username, password, phone);
        if (!validationResult.equals("OK")) {
            showMessage(validationResult, "red");
            return;
        }

        // Insert into DB
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.insertUserLibrarian(username, password, "librarian", phone);

        if (success) {
            showMessage("✔ Librarian Added Successfully", "green");
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> closeWindow());
            pause.play();
        } else {
            showMessage("✖ Failed to Add Librarian", "red");
        }
    }

    // ---------------- Helper Methods ----------------
    private void showMessage(String text, String color) {
        if (AddLibMsgLabel != null) {
            AddLibMsgLabel.setText(text);
            AddLibMsgLabel.setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveBtnAddLibrarian.getScene().getWindow();
        if (stage != null) stage.close();
    }
}
