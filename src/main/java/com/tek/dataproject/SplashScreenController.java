package com.tek.dataproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SplashScreenController extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(SplashScreenController.class.getResource("splash-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 820);
        stage.setMaximized(true);
        stage.setTitle("Exoplanet Analyzer");
        stage.setScene(scene);
        stage.show();
    }
}
