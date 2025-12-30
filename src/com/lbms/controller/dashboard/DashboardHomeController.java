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
import java.util.Objects;

public class DashboardHomeController {

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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LoadScene.load(stage, "/fxml/auth/LoginView.fxml", "Login");
    }

    private void loadRightPane(String fxmlPath) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource(fxmlPath));
            if (pane != null) {
                // Add CSS to the loaded pane so styleClass works
                pane.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/css/rightPaneButton.css")).toExternalForm()
                );

                dashRightPane.getChildren().setAll(pane);
            } else {
                System.err.println("FXML not found: " + fxmlPath);
            }
        } catch (IOException e) {
            System.err.println("Failed to load FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }


}
