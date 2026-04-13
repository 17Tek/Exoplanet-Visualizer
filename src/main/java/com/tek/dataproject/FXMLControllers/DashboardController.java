package com.tek.dataproject.FXMLControllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController extends Application
{

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(DashboardController.class.getResource("/com/tek/dataproject/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 820);
        stage.setMaximized(true);
        stage.setTitle("Exoplanet Analyzer");
        stage.setScene(scene);
        stage.show();

    }

}
