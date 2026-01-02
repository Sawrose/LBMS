package com.lbms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoadScene {

    public static void load(Stage stage, String fxmlPath, String title) {
        try {
            // Load FXML
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(LoadScene.class.getResource(fxmlPath), "FXML file not found: " + fxmlPath)
            );

            // Create scene
            Scene scene = new Scene(root);

            // Load CSS files
            // Always add global styles
            addCSS(scene, "/css/style.css");
            addCSS(scene, "/css/globalFont.css");
            addCSS(scene, "/css/tableStyles.css");

            // Add Left/Right Pane button styles for all scenes except LoginView
            if (!fxmlPath.contains("LoginView")) {
                addCSS(scene, "/css/LeftPaneButton.css");
                addCSS(scene, "/css/RightPaneButton.css");
            }

            // Apply scene to stage
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to safely add CSS to the scene
     */
    private static void addCSS(Scene scene, String cssPath) {
        try {
            scene.getStylesheets().add(
                    Objects.requireNonNull(
                            LoadScene.class.getResource(cssPath),
                            "CSS file not found: " + cssPath
                    ).toExternalForm()
            );
        } catch (NullPointerException e) {
            System.err.println("Failed to load CSS: " + cssPath);
            e.printStackTrace();
        }
    }
}
