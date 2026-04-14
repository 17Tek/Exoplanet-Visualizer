package com.tek.dataproject;

import com.tek.dataproject.FXMLControllers.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

    public class AppLauncher extends Application{

        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/tek/dataproject/dashboard.fxml")
            );
            Scene scene = new Scene(loader.load(), 1440, 820);
            stage.setMaximized(true);
            stage.setTitle("Exoplanet Analyzer");
            stage.setScene(scene);
            stage.show();

        }
    }
