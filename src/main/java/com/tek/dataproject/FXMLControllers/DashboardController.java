package com.tek.dataproject.FXMLControllers;

import com.tek.dataproject.Services.ExoplanetService;
import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

@Component
public class DashboardController {

    @Autowired
    private ExoplanetService exoplanetService;

    @FXML private Label statusLabel1;
    @FXML private Label planetNameDisplay;
    @FXML private Label hostStarNameDisplay;
    @FXML private Button generateRandomPlanet;
    @FXML private StackPane systemPane;
    @FXML private StackedAreaChart stackedAreaChart;



    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            try {
                renderSystem("Proxima Cen");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void renderSystem(String hostStar) {
        systemPane.getChildren().removeIf(node -> !(node instanceof ImageView)); //Overrides everything except the background image of space

        List<Exoplanet> planets = exoplanetService.findHostStar(hostStar);

        double width = systemPane.getWidth();
        double height = systemPane.getHeight();
        double centerY = height / 2;

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

    }

    @FXML
    public void handleRandomPlanet() {
        Exoplanet planet = exoplanetService.findRandomPlanet();
        selectPlanet(planet);
    }

    public void selectPlanet(Exoplanet planet) {
        planetNameDisplay.setText(planet.planetName());
        hostStarNameDisplay.setText(planet.hostStar());
        renderSystem(planet.hostStar());
    }

    public void handleBarChart(Exoplanet planet){

        double lightYears;
        double miles;
        double kilometers;

        double distPC = planet.distFromEarthPc();
        System.out.println(distPC);




        /* Conversion factors for a parsec

    3.26156 light-years (ly)
30.86 trillion kilometers (

 km)
19.17 trillion miles (

 miles)
206,265 Astronomical Units (AU)
     */
    }



}
