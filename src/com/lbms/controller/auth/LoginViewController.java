package com.lbms.controller.auth;

import com.lbms.dao.UserDAO;
import com.lbms.model.User;
import com.lbms.util.LoadScene;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class LoginViewController {

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
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/view.png")))
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
                    new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/hide.png")))
            );

            isPasswordVisible = true;
        }
    }

    // Login handler
    @FXML
    private void handleLogin(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String username = UnameField.getText();
        String password = isPasswordVisible
                ? PwVisibleField.getText()
                : PwField.getText();

        User loggedInUser = userDAO.login(username, password);
        if (loggedInUser != null) {

            loginMSGlabel.setText("Login successful!");
            loginMSGlabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(e -> {
                switch (loggedInUser.getRole().toLowerCase()) {
                    case "admin":
                        LoadScene.load(stage,
                                "/fxml/dashboard/DashboardLandingPage.fxml",
                                "Admin Dashboard");
                        break;

                    case "librarian":
                        LoadScene.load(stage,
                                "/fxml/librarian/LibrarianLandingPage.fxml",
                                "Librarian Panel");
                        break;

                    default:
                        loginMSGlabel.setText("Unknown role!");
                        loginMSGlabel.setStyle("-fx-text-fill: red;");
                        clearMessageAfterDelay();
                }
            });
            pause.play();

        } else {
            loginMSGlabel.setText("Username or password is wrong!");
            loginMSGlabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            clearMessageAfterDelay();
        }
    }





    private void clearMessageAfterDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> loginMSGlabel.setText(""));
        pause.play();
    }
}
