package com.lbms.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoadScene {

    public static void load(Stage stage, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(LoadScene.class.getResource(fxmlPath))
            );

            Scene scene = new Scene(root);

            if (!fxmlPath.contains("LoginView")) {
                scene.getStylesheets().add(
                        Objects.requireNonNull(
                                LoadScene.class.getResource("/css/LeftPaneButton.css")
                        ).toExternalForm()
                );
                scene.getStylesheets().add(
                        Objects.requireNonNull(
                                LoadScene.class.getResource("/css/RightPaneButton.css")
                        ).toExternalForm()
                );
            }

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
