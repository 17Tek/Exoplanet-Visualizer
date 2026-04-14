package com.tek.dataproject.FXMLControllers;

import com.tek.dataproject.Services.ExoplanetService;
import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.util.*;

@Component
public class DashboardController {

    @Autowired
    private ExoplanetService exoplanetService;

    @FXML private Label statusLabel1;
    @FXML private Label planetNameDisplay;
    @FXML private Label hostStarNameDisplay;
    @FXML private Button generateRandomPlanet;
    @FXML private StackPane systemPane;
    @FXML private BarChart<String, Number> barChart;


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
        setBarChart(exoplanetService.travelTimes(planet), planet);
    }

    public void setBarChart(Map<String, Double> time, Exoplanet exoplanet) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Travel Time (Years)");

        for (Map.Entry<String, Double> entry : time.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
    }





}
