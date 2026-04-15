package com.tek.dataproject.FXMLControllers;

import com.tek.dataproject.Services.CenterPanelService;
import com.tek.dataproject.Services.DataDisplayService;
import com.tek.dataproject.Services.ExoplanetService;
import com.tek.dataproject.Services.SolarSystemPlanetService;
import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.util.*;

@Component
public class DashboardController {

    // Service layer instances — connect the UI to the business logic
    @Autowired private ExoplanetService exoplanetService;
    @Autowired private DataDisplayService dataDisplayService;
    @Autowired private SolarSystemPlanetService solarSystemPlanetService;
    @Autowired private CenterPanelService centerPanelService;

    //The connection to the actual ui fields that are interactive with the backend
    //FXML makes sure that javaFX injects the values when it loads the fxml
    @FXML private Label statusLabel1;
    @FXML private Label planetNameDisplay;
    @FXML private Label hostStarNameDisplay;
    @FXML private StackPane systemPane;
    @FXML private BarChart<String, Number> barChart;
    @FXML private Hyperlink hyperLink;
    @FXML private Label urlLabel;

    //This is a java fx class needed to bridge the app and the operating system, allowing me to open the document with whatever the default is
    private HostServices hostServices;
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    //JavaFX runs this automatically when the FXML is loaded adn all FXML fields are injected
    // since i'm using canvas for the design of the planets I need to render the system after javafx finishes laying out the scene, without it the canvas cannot draw anything
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

    //This will generate the canvas images based on the system that the exoplanet is from
    public void renderSystem(String hostStar) {
        systemPane.getChildren().removeIf(node -> !(node instanceof ImageView)); //This will make sure that the gc will be in front of everything

        List<Exoplanet> planets = exoplanetService.findHostStar(hostStar);

        double width = systemPane.getWidth();
        double height = systemPane.getHeight();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        centerPanelService.drawSystem(gc, planets, width, height);

        systemPane.getChildren().add(canvas);
    }

    @FXML //This method will handle the action of the random planet button producing a planet with the list of all the necessary values from the Repository
    public void handleRandomPlanet() {
        Exoplanet planet = exoplanetService.findRandomPlanet();
        selectPlanet(planet);
    }

    //This method will handle things like labeling the planets title and host star, along with calling render system, and creating the hyperLink
    public void selectPlanet(Exoplanet planet) {
        planetNameDisplay.setText(planet.planetName());
        hostStarNameDisplay.setText(planet.hostStar());
        renderSystem(planet.hostStar());
        setBarChart(exoplanetService.travelTimes(planet));

        String url = exoplanetService.processLinkForPlanet(planet);
        hyperLink.setOnAction(e -> hostServices.showDocument(url));
        hyperLink.setText(url);
        urlLabel.setText("Link to NASA:");
    }

    //This method uses an external library to add the values to the FXML bar chart, it uses a logarithmic scaling to have the graph be more understandable //TODO fix the ui and simplify
    public void setBarChart(Map<String, Double> travelTimes) {
        barChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Travel Time (Years)");

        for (Map.Entry<String, Double> entry : travelTimes.entrySet()) {
            double logValue = Math.log10(entry.getValue());
            series.getData().add(new XYChart.Data<>(entry.getKey(), logValue));
        }

        barChart.getData().add(series);
    }
}
