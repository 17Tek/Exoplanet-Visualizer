package com.tek.dataproject;

import com.tek.dataproject.FXMLControllers.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppLauncher extends Application {

    private ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override //Boots spring and gets all of the spring beans ready like the repo or service
    public void init() {
        springContext = SpringApplication.run(AppLauncher.class, new String[]{});
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tek/dataproject/dashboard.fxml"));

            //This line is needed to connect spring to the controller instead of javafx, this allowed me to use dependency injections
            loader.setControllerFactory(springContext::getBean);
            Scene scene = new Scene(loader.load());
            DashboardController controller = loader.getController();

            //This tool is used to help open the browser when you click on a link
            controller.setHostServices(getHostServices());
            stage.setTitle("Exoplanet Analyzer");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        springContext.close();
    }
}

