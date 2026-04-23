package com.tek.dataproject.Services;

import com.tek.dataproject.TableRecord.Exoplanet;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CenterPanelService
{

    public void drawSystem(GraphicsContext gc, List<Exoplanet> planets, double width, double height, String generatedPlanetName) {

        //TODO fix all of the gap bugs and the size bugs

        if (planets.isEmpty()) return; //edge case check

        //gets the first planet in the list of planets with the same star
        Exoplanet first = planets.get(0);

        //where to start on the canvas
        int startStar = 60;
        //checks if the row pulls contains a radius value, if it does it set it to whatever the measurment is and then gives it a minimum value so that it is always visible
        double starRadius = (first.starRadiusSun() != null) ? first.starRadiusSun() * 50 : 20;
        starRadius = Math.min(starRadius, height * 0.35);
        double centerY = height / 2; //center of the canvas

        // star art
        gc.setFill(getStarColor(planets.get(0).starTempK()));
        gc.fillOval(startStar, centerY - starRadius, starRadius * 2, starRadius * 2);

        //planet art
        for (int i = 0; i < planets.size(); i++) { //loops until all planets are created from the system
            Exoplanet planet = planets.get(i);

            //This is the math to determine spacing and size of each given planet, using ternary to find edge cases and handle them
            double planetSize = (planet.planetRadiusEarth() != null) ? Math.max(6, planet.planetRadiusEarth() * 8) : 6;
            double planetX = (planet.semiMajorAxisAu() != null) ? 200 + planet.semiMajorAxisAu() * 100 : 200 + i * 120;
            planetX = Math.max(planetX, 200 + i * (planetSize + 20));

            // base color
            gc.setFill(getPlanetColor(planet.planetType()));
            gc.fillOval(planetX, centerY - planetSize / 2, planetSize, planetSize);
            if (planet.planetName().equals(generatedPlanetName)) {
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1);
                gc.strokeText(planet.planetName(), planetX, centerY - planetSize / 2 - 8);
            }

            //planet orbit art
            double starCenter = startStar + starRadius;
            drawOrbit(gc, starCenter, centerY, planetX, planet.planetRadiusEarth());
        }
    }

    public void drawOrbit(GraphicsContext gc, double starCenter, double center,double planetX, double planetRadius){

        double orbitRadius = planetX - starCenter;

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeOval(starCenter - orbitRadius, center - orbitRadius / 2, orbitRadius * 2, orbitRadius);
    }

    public Color getStarColor(Double starTemp){

        if(starTemp == null)return Color.YELLOW;

        if (starTemp < 3700){
            return Color.RED;
        }
        if(starTemp < 5200){
            return Color.DARKORANGE;
        }
        if(starTemp < 6000){
            return Color.YELLOW;
        }
        if(starTemp < 10000){
            return Color.LIGHTYELLOW;
        }
        if(starTemp < 30000){
            return Color.DEEPSKYBLUE;
        }
        return Color.YELLOW;
//                O (Blue): >30,000 K (up to 50,000 K).
//                B (Blue-White): 10,000 K – 30,000 K.
//                A (White): 7,500 K – 10,000 K.
//                F (Yellow-White): 6,000 K – 7,500 K.
//                G (Yellow - Sun): 5,200 K – 6,000 K.
//                K (Orange): 3,700 K – 5,200 K.
//                M (Red): 2,400 K – 3,700 K
    }


        public Color getPlanetColor(String planetType) {
            if (planetType == null) return Color.LIGHTGRAY;
            return switch (planetType) {
                case "Gas Giant" -> Color.ORANGE;
                case "Neptune-like" -> Color.CORNFLOWERBLUE;
                case "Super Earth" -> Color.LIGHTGREEN;
                case "Terrestrial" -> Color.TAN;
                default -> Color.LIGHTBLUE;
            };
        }
    }

