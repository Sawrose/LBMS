package com.lbms.controller;

import com.lbms.dao.UserDAO;
import com.lbms.model.users;
import com.lbms.util.AlertUtil;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class mainController {

    @FXML
    private TextField UnameField;

    @FXML
    private PasswordField PwField;

    @FXML
    private TextField PwVisibleField;

    @FXML
    private Label loginMSGlabel;

    @FXML
    private ImageView LoginOpenEye;

    @FXML
    private Button LoginBtn;

    private final UserDAO userDAO = new UserDAO();
    private boolean isPasswordVisible = false;

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
            LoginOpenEye.setImage(
                    new Image(getClass().getResourceAsStream("/images/view.png"))
            );

            isPasswordVisible = false;
        } else {
            // Show password
            PwVisibleField.setText(PwField.getText());
            PwVisibleField.setVisible(true);
            PwVisibleField.setManaged(true);

            PwField.setVisible(false);
            PwField.setManaged(false);

            // Change eye icon
            LoginOpenEye.setImage(
                    new Image(getClass().getResourceAsStream("/images/hide.png"))
            );

            isPasswordVisible = true;
        }
    }

    // Login handler
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = UnameField.getText();
        String password = isPasswordVisible
                ? PwVisibleField.getText()
                : PwField.getText();

        // Check username
        if (username.isEmpty()) {
            loginMSGlabel.setText("Please Enter Username !");
            loginMSGlabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            clearMessageAfterDelay();
            return;
        }

        // Check password
        if (password.isEmpty()) {
            loginMSGlabel.setText("Please Enter Password !");
            loginMSGlabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            clearMessageAfterDelay();
            return;
        }

        users loggedInUser = userDAO.login(username, password);

        if (loggedInUser != null) {
            dashboard(event);
        } else {
            loginMSGlabel.setText("Invalid username or password !");
            loginMSGlabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            clearMessageAfterDelay();
        }

    }

    private void dashboard(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/dashboard.fxml")
            );

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene()
                    .getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to clear the label after 1 second
    private void clearMessageAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> loginMSGlabel.setText(""));
        pause.play();
    }
}
