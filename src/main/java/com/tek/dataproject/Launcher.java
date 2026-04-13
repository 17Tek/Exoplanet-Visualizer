package com.tek.dataproject;

import javafx.application.Application;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {

    public static AnnotationConfigApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(DatabaseConfiguration.class);
        Application.launch(SplashScreenController.class, args);
    }
}