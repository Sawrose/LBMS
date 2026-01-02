package com.lbms.controller.auth;

import com.lbms.dao.UserDAO;
import com.lbms.model.User;
import com.lbms.util.LoadScene;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginViewController {

    private static final Logger logger = Logger.getLogger(LoginViewController.class.getName());

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

    /** Toggle password visibility */
    @FXML
    private void togglePassword() {
        try {
            if (isPasswordVisible) {
                PwField.setText(PwVisibleField.getText());
                PwField.setVisible(true);
                PwField.setManaged(true);

                PwVisibleField.setVisible(false);
                PwVisibleField.setManaged(false);

                LoginOpenEye.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/images/view.png"),
                                "view.png not found")));
                isPasswordVisible = false;
            } else {
                PwVisibleField.setText(PwField.getText());
                PwVisibleField.setVisible(true);
                PwVisibleField.setManaged(true);

                PwField.setVisible(false);
                PwField.setManaged(false);

                LoginOpenEye.setImage(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/images/hide.png"),
                                "hide.png not found")));
                isPasswordVisible = true;
            }
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "FXML or image resource not found", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in togglePassword()", e);
        }
    }

    /** Handle login button click */
    @FXML
    private void handleLogin(ActionEvent event) {
        Stage stage = null;

        try {
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        } catch (NullPointerException e) {
            logger.log(Level.SEVERE, "Stage is null. Cannot proceed with login.", e);
            return;
        }

        String username = UnameField.getText();
        String password = isPasswordVisible ? PwVisibleField.getText() : PwField.getText();

        loginMSGlabel.setText("Logging in...");
        loginMSGlabel.setStyle("-fx-text-fill: blue;");

        // Run login in background thread
        Task<User> task = new Task<>() {
            @Override
            protected User call() {
                try {
                    return userDAO.login(username, password);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error during DAO login", e);
                    return null;
                }
            }
        };

        Stage finalStage = stage;
        task.setOnSucceeded(e -> {
            try {
                User loggedInUser = task.getValue();

                if (loggedInUser != null) {
                    loginMSGlabel.setText("Login successful!");
                    loginMSGlabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

                    PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                    pause.setOnFinished(ev -> {
                        try {
                            switch (loggedInUser.getRole().toLowerCase()) {
                                case "admin" -> LoadScene.load(finalStage,
                                        "/fxml/dashboard/DashboardLandingPage.fxml",
                                        "Admin Dashboard");
                                case "librarian" -> LoadScene.load(finalStage,
                                        "/fxml/librarian/LibrarianLandingPage.fxml",
                                        "Librarian Panel");
                                default -> {
                                    loginMSGlabel.setText("Unknown role!");
                                    loginMSGlabel.setStyle("-fx-text-fill: red;");
                                    clearMessageAfterDelay();
                                }
                            }
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, "Error loading next scene", ex);
                            loginMSGlabel.setText("Cannot load dashboard");
                            loginMSGlabel.setStyle("-fx-text-fill: red;");
                        }
                    });
                    pause.play();
                } else {
                    loginMSGlabel.setText("Username or password is wrong!");
                    loginMSGlabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    clearMessageAfterDelay();
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Unexpected error in login success handler", ex);
            }
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            logger.log(Level.SEVERE, "Login task failed", ex);
            loginMSGlabel.setText("Login error!");
            loginMSGlabel.setStyle("-fx-text-fill: red;");
        });

        new Thread(task).start();
    }

    /** Clears login message after a short delay */
    private void clearMessageAfterDelay() {
        try {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> loginMSGlabel.setText(""));
            pause.play();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error clearing login message", e);
        }
    }
}
