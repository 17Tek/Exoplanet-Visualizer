package com.tek.dataproject.FXMLControllers;

import com.tek.dataproject.Services.CenterPanelService;
import com.tek.dataproject.Services.ExoplanetService;
import com.tek.dataproject.Services.SolarSystemPlanetService;
import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DashboardController {

    // Service layer instances — connect the UI to the business logic
    @Autowired
    private ExoplanetService exoplanetService;
    @Autowired
    private CenterPanelService centerPanelService;
    @Autowired
    private SolarSystemPlanetService solarSystemPlanetService;

    //The connection to the actual ui fields that are interactive with the backend
    //FXML makes sure that javaFX injects the values when it loads the fxml

    //top planet and system display
    @FXML
    private Label planetNameDisplay;
    @FXML
    private Label hostStarNameDisplay;
    @FXML
    private StackPane systemPane;
    @FXML
    private Hyperlink hyperLink;
    @FXML
    private Label urlLabel;

    //split pane fxml with the charts
    @FXML
    private ScatterChart<String, Number> scatterChart;
    @FXML
    private BarChart<String, Number> barChart;

    //Left Vbox FXML
    @FXML
    private ToggleButton typeGasGiant;
    @FXML
    private ToggleButton typeSuperEarth;
    @FXML
    private ToggleButton typeNeptune;
    @FXML
    private ToggleButton typeMiniNeptune;
    @FXML
    private ToggleButton typeSubEarth;
    @FXML
    private ToggleButton typeSuperJupiter;
    @FXML
    private ToggleButton typeUnknown;
    @FXML
    private ToggleButton methodTransit;
    @FXML
    private ToggleButton methodRadial;
    @FXML
    private ToggleButton methodImaging;
    @FXML
    private ToggleButton habitableToggle;
    @FXML
    private Label errorBox;

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
                renderSystem("Sun", null);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    @FXML //sets the filter values back to their original preset
    private void handleClearFilters(){
        typeGasGiant.setSelected(false);
        typeSuperEarth.setSelected(false);
        typeNeptune.setSelected(false);
        typeMiniNeptune.setSelected(false);
        typeSubEarth.setSelected(false);
        typeSuperJupiter.setSelected(false);
        typeUnknown.setSelected(false);
        methodTransit.setSelected(false);
        methodRadial.setSelected(false);
        methodImaging.setSelected(false);
        habitableToggle.setSelected(false);
        errorBox.setText("");
    }

    @FXML //uses the filters
    private void handleApplyFilters() throws Exception {
        //this data will be used to search for a possible valid planet
        String selectedPlanetType = null;
        if (typeGasGiant.isSelected()) selectedPlanetType = "Gas Giant";
        else if (typeSuperEarth.isSelected()) selectedPlanetType = "Super-Earth";
        else if (typeNeptune.isSelected()) selectedPlanetType = "Neptune-like";
        else if (typeMiniNeptune.isSelected()) selectedPlanetType = "Mini-Neptune";
        else if (typeSubEarth.isSelected()) selectedPlanetType = "Sub-Earth";
        else if (typeSuperJupiter.isSelected()) selectedPlanetType = "Super-Jupiter";
        else if (typeUnknown.isSelected()) selectedPlanetType = "Unknown";

        String selectedDiscoveryMethod = null;
        if (methodTransit.isSelected()) selectedDiscoveryMethod = "Transit";
        else if (methodRadial.isSelected()) selectedDiscoveryMethod = "Radial Velocity";
        else if (methodImaging.isSelected()) selectedDiscoveryMethod = "Imaging";

        Boolean habitableOnly = habitableToggle.isSelected() ? true : null;

// If habitable is on, force Super-Earth
        if (habitableOnly != null && habitableOnly) {
            selectedPlanetType = "Super-Earth";
            errorBox.setText("Habitable filter overrides planet type");
        }

        //Searches and returns the list of planets with the correct search filters applied
        List<Exoplanet> listFilteredPlanets = exoplanetService.findByFilters(selectedPlanetType, selectedDiscoveryMethod, habitableOnly);
        if (listFilteredPlanets.isEmpty()) {return;}

        Random rand = new Random();
        //gets a random index from the result to use for the rendered system
        Exoplanet exoplanet = listFilteredPlanets.get(rand.nextInt(listFilteredPlanets.size()));
        selectPlanet(exoplanet);
    }

    //This will generate the canvas images based on the system that the exoplanet is from
    public void renderSystem(String hostStar, String planetName) {
        systemPane.getChildren().removeIf(node -> node instanceof Canvas); //This will make sure that the gc will be in front of everything

        List<Exoplanet> planets = exoplanetService.findHostStar(hostStar);

        double width = systemPane.getWidth();
        double height = systemPane.getHeight();

        Canvas canvas = new Canvas(width, height);
        canvas.setManaged(false);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        centerPanelService.drawSystem(gc, planets, width, height, planetName);
        systemPane.getChildren().add(canvas);
    }

    @FXML
    //This method will handle the action of the random planet button producing a planet with the list of all the necessary values from the Repository
    public void handleRandomPlanet() throws Exception {
        Exoplanet planet = exoplanetService.findRandomPlanet();
        selectPlanet(planet);
    }

    //This method will handle things like labeling the planets title and host star, along with calling render system, and creating the hyperLink
    public void selectPlanet(Exoplanet planet) throws Exception {
        try {
            planetNameDisplay.setText(planet.planetName());
            hostStarNameDisplay.setText(planet.hostStar());
            renderSystem(planet.hostStar(), planet.planetName());
            setBarChart(exoplanetService.travelTimes(planet));
            setScatterChart(exoplanetService.findHostStar(planet.hostStar()));

            String url = exoplanetService.processLinkForPlanet(planet);
            hyperLink.setOnAction(e -> hostServices.showDocument(url));
            hyperLink.setText(url);
            urlLabel.setText("Link to NASA:");

        } catch (
                Exception e) {  //This clears up almost every exception that could come up, just finds a new random planet if anything goes wrong
            System.out.println(e.getMessage());
            handleRandomPlanet();
        }
    }


    public void setScatterChart(List<Exoplanet> planets) {
        scatterChart.getData().clear();
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Planet Temperature (K)");
        series1.getData().add(new XYChart.Data<>("Earth", 255));

        for(Exoplanet p : planets) {
            //same as setBarChart
            if (p.equilibriumTempK() != null) {
                series1.getData().add(new XYChart.Data<>(p.planetName(), p.equilibriumTempK()));
            }
        }
        scatterChart.getData().add(series1);
    }


    //This method uses an external library to add the values to the FXML bar chart, it uses a logarithmic scaling to have the graph be more understandable
    public void setBarChart(Map<String, Double> travelTimes) {
        //creates the first bar in the chart
        XYChart.Series series1 = new XYChart.Series();
        //Sets the name for that bar
        series1.setName("New Horizon Years");
        //Gets the data for that bar by entering the key for the map and doing a log operation on the time it takes
        series1.getData().add(new XYChart.Data<>("New Horizons", Math.log10(travelTimes.get("New Horizons"))));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Voyager Years");
        series2.getData().add(new XYChart.Data<>("Voyager 1", Math.log10(travelTimes.get("Voyager 1"))));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("At light speed");
        series3.getData().add(new XYChart.Data<>("At Light Speed", Math.log10(travelTimes.get("At Light Speed"))));

        XYChart.Series series4 = new XYChart.Series();
        series4.setName("1% Light Speed");
        series4.getData().add(new XYChart.Data<>("1% Light Speed", Math.log10(travelTimes.get("1% Light Speed"))));

        barChart.getData().clear();
        barChart.getData().addAll(series1, series2, series3, series4);

    }
}
