package com.tek.dataproject.FXMLControllers;

import com.tek.dataproject.Services.CenterPanelService;
import com.tek.dataproject.Services.DataDisplayService;
import com.tek.dataproject.Services.ExoplanetService;
import com.tek.dataproject.Services.SolarSystemPlanetService;
import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.image.ImageView;

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

    //top planet and system display
    @FXML private Label planetNameDisplay;
    @FXML private Label hostStarNameDisplay;
    @FXML private StackPane systemPane;
    @FXML private Hyperlink hyperLink;
    @FXML private Label urlLabel;

    //split pane fxml with the charts
    @FXML private StackedAreaChart <String, Number> stackedAreaChart;
    @FXML private BarChart<String, Number> barChart;

    //Left Vbox FXML
    @FXML private TextField planetSearchField;
    @FXML private ComboBox<String> planetTypeComboBox;
    @FXML private ComboBox<String> discoveryMethodComboBox;
    @FXML private ToggleButton habitableToggle;
    @FXML private ToggleButton recentToggle;
    @FXML private Button applyFiltersButton;
    @FXML private Button clearFiltersButton;
    @FXML private Label resultsCountLabel;


    //This is a java fx class needed to bridge the app and the operating system, allowing me to open the document with whatever the default is
    private HostServices hostServices;
    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }

    //JavaFX runs this automatically when the FXML is loaded adn all FXML fields are injected
    // since i'm using canvas for the design of the planets I need to render the system after javafx finishes laying out the scene, without it the canvas cannot draw anything
    @FXML
    public void initialize() {
        setupComboBoxes();
        Platform.runLater(() -> {
            try {
                renderSystem("Sun", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void setupComboBoxes() {
        planetTypeComboBox.getItems().addAll("All Types", "Rocky", "Gas Giant", "Super Earth");
        planetTypeComboBox.setValue("All Types");
        discoveryMethodComboBox.getItems().addAll("All Methods", "Transit", "Radial Velocity", "Imaging");
        discoveryMethodComboBox.setValue("All Methods");
    }

    @FXML //uses the filters
    private void handleApplyFilters() throws Exception{
        //this data will be used to search for a possible valid planet
        String selectedPlanetType = planetTypeComboBox.getValue();
        String selectedDiscoveryMethod = discoveryMethodComboBox.getValue();
        if ("All Types".equals(selectedPlanetType)) {
            selectedPlanetType = null;
        }

        if ("All Methods".equals(selectedDiscoveryMethod)) {
            selectedDiscoveryMethod = null;
        }

        Boolean habitableOnly = habitableToggle.isSelected() ? true : null;

        List<Exoplanet> results = exoplanetService.searchWithFilters(
                selectedPlanetType,
                selectedDiscoveryMethod,
                habitableOnly
        );

        System.out.println("Filtered results: " + results.size());

        if (results.isEmpty()) {
            return;
        }

        selectPlanet(results.get(0));
    }

    @FXML //clears all the values to null when called
    private void handleClearFilters(){
        String selectedPlanetType = null;
        String selectedDiscoveryMethod = null;
        boolean habitableOnly = false;
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

    @FXML //This method will handle the action of the random planet button producing a planet with the list of all the necessary values from the Repository
    public void handleRandomPlanet() throws Exception{
        Exoplanet planet = exoplanetService.findRandomPlanet();
        selectPlanet(planet);
    }

    //This method will handle things like labeling the planets title and host star, along with calling render system, and creating the hyperLink
    public void selectPlanet(Exoplanet planet) throws Exception{
        try {
            planetNameDisplay.setText(planet.planetName());
            hostStarNameDisplay.setText(planet.hostStar());
            renderSystem(planet.hostStar(), planet.planetName());
            setBarChart(exoplanetService.travelTimes(planet));
            setStackedAreaChart(exoplanetService.findHostStar(planet.hostStar()));

            String url = exoplanetService.processLinkForPlanet(planet);
            hyperLink.setOnAction(e -> hostServices.showDocument(url));
            hyperLink.setText(url);
            urlLabel.setText("Link to NASA:");

        } catch (Exception e){  //This clears up almost every exception that could come up, just finds a new random planet if anything goes wrong
            System.out.println(e.getMessage());
            handleRandomPlanet();
        }
    }

    public void setStackedAreaChart(List<Exoplanet> planets){
        stackedAreaChart.getData().clear();

        // Sort by AU, nulls go to end
        List<Exoplanet> sorted = planets.stream()
                .sorted(Comparator.comparingDouble(p -> p.semiMajorAxisAu() != null ? p.semiMajorAxisAu() : 0.0))
                .collect(java.util.stream.Collectors.toList());

        XYChart.Series<String, Number> radiusSeries = new XYChart.Series<>();
        radiusSeries.setName("Radius (Earth = 1)");

        XYChart.Series<String, Number> massSeries = new XYChart.Series<>();
        massSeries.setName("Mass (Earth = 1)");

        // Inject Earth at 1.0 AU as reference
        radiusSeries.getData().add(new XYChart.Data<>("Earth (1.0 AU)", 1.0));
        massSeries.getData().add(new XYChart.Data<>("Earth (1.0 AU)", 1.0));

        for (Exoplanet p : sorted) {
            String label = p.planetName() + " (" + p.semiMajorAxisAu() + " AU)";
            radiusSeries.getData().add(new XYChart.Data<>(label, p.planetRadiusEarth() != null ? p.planetRadiusEarth() : 1.0));
            massSeries.getData().add(new XYChart.Data<>(label, p.planetMassEarth() != null ? p.planetMassEarth() : 1.0));
        }

        stackedAreaChart.getData().addAll(radiusSeries, massSeries);
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
