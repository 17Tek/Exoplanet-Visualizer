package com.tek.dataproject.FXMLControllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class DashboardController
{

    @FXML private Label statusLabel1;
    @FXML private Label planetNameDisplay;
    @FXML private Label hostStarNameDisplay;

    @FXML
    public void handleLabelChanges(String text){
        planetNameDisplay.setText(text);
    }


}
