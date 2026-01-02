package com.lbms.controller.dashboard;

import com.lbms.util.LoadScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardHomeController {

    private static final Logger logger = Logger.getLogger(DashboardHomeController.class.getName());

    @FXML
    private Pane dashRightPane;

    @FXML
    public void initialize() {
        loadRightPane("/fxml/dashboard/DashboardHome.fxml");
    }

    @FXML
    private void DboardDashButClickHandle() {
        loadRightPane("/fxml/dashboard/DashboardHome.fxml");
    }

    @FXML
    private void DboardLibButClickHandle() {
        loadRightPane("/fxml/librarian/LibrarianHome.fxml");
    }

    @FXML
    private void DboardBooksButClickHandle() {
        loadRightPane("/fxml/Books.fxml");
    }

    @FXML
    private void DboardStdtButClickHandle() {
        loadRightPane("/fxml/Students.fxml");
    }

    @FXML
    private void DboardRepoButClickHandle() {
        loadRightPane("/fxml/Reports.fxml");
    }

    @FXML
    private void DboardLoutButClickHandle(ActionEvent event) {
        try {
            Node sourceNode = (Node) event.getSource();
            if (sourceNode == null || sourceNode.getScene() == null) {
                logger.warning("Logout failed: source node or scene is null");
                return;
            }

            Stage stage = (Stage) sourceNode.getScene().getWindow();
            if (stage == null) {
                logger.warning("Logout failed: stage is null");
                return;
            }

            // Exception-safe scene load
            LoadScene.load(stage, "/fxml/auth/LoginView.fxml", "Login");

        } catch (ClassCastException e) {
            logger.log(Level.SEVERE, "Logout failed: event source is not a Node or stage cast failed", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error during logout", e);
        }
    }

    private void loadRightPane(String fxmlPath) {
        if (dashRightPane == null) {
            logger.warning("Cannot load pane: dashRightPane is null");
            return;
        }

        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            if (fxmlUrl == null) {
                logger.severe("FXML file not found: " + fxmlPath);
                return;
            }

            AnchorPane pane = FXMLLoader.load(fxmlUrl);
            if (pane == null) {
                logger.severe("Loaded pane is null for FXML: " + fxmlPath);
                return;
            }

            // Load CSS safely
            URL cssUrl = getClass().getResource("/css/rightPaneButton.css");
            if (cssUrl != null) {
                pane.getStylesheets().add(cssUrl.toExternalForm());
            } else {
                logger.warning("CSS file not found: /css/rightPaneButton.css");
            }

            dashRightPane.getChildren().setAll(pane);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load FXML: " + fxmlPath, e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while loading pane: " + fxmlPath, e);
        }
    }
}
