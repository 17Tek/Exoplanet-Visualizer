package com.tek.dataproject;

import com.tek.dataproject.FXMLControllers.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import java.io.IOException;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppLauncher extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(AppLauncher.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/tek/dataproject/dashboard.fxml")
        );
        loader.setControllerFactory(springContext::getBean);
        Scene scene = new Scene(loader.load(), 1440, 820);

        DashboardController controller = loader.getController();
        controller.setHostServices(getHostServices());

        stage.setMaximized(true);
        stage.setTitle("Exoplanet Analyzer");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}