package com.lbms.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class mainController {

    @FXML
    private TextField UnameField;

    @FXML
    private PasswordField PwField;

    @FXML
    private TextField PwVisibleField;

    @FXML
    private Label jstPrint;

    @FXML
    private Label messageLabel;

    @FXML
    private ImageView LoginOpenEye;

    private boolean isPasswordVisible = false;


    // 🔹 Login handler
    @FXML
    private void handleLogin() {
        String username = UnameField.getText();
        String password = isPasswordVisible ? PwVisibleField.getText() : PwField.getText();

        if (username.equals("admin") && password.equals("admin")) {
            messageLabel.setText("Login Successful");
        } else {
            messageLabel.setText("Invalid Credentials");
        }
    }


    // Toggle password visibility Login Page start here
    @FXML
    private void togglePassword() {
        if (isPasswordVisible) {
            // Hide password
            PwField.setText(PwVisibleField.getText());
            PwField.setVisible(true);
            PwField.setManaged(true);

            PwVisibleField.setVisible(false);
            PwVisibleField.setManaged(false);

            // Change eye icon
            LoginOpenEye.setImage(new Image(getClass().getResourceAsStream("/images/view.png")));

            isPasswordVisible = false;
        } else {
            // Show password
            PwVisibleField.setText(PwField.getText());
            PwVisibleField.setVisible(true);
            PwVisibleField.setManaged(true);

            PwField.setVisible(false);
            PwField.setManaged(false);

            // Change eye icon
            LoginOpenEye.setImage(new Image(getClass().getResourceAsStream("/images/hide.png")));

            isPasswordVisible = true;
        }
    }

    // Toggle password visibility Login Page ends here
    // Toggle password visibility Login Page ends here

}
