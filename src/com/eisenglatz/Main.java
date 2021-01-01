package com.eisenglatz;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private ChartModule chartModule;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(4000));
        seed.add(new Carbon(8000));
//        seed.add(new CarbonDioxide(2));
        HashMap<String, Planet> Sol = new HashMap<String, Planet>();
        Planet newPlanet = new Planet("Earth", seed);
        Sol.put(newPlanet.getPlanetName(), newPlanet);

        infest(newPlanet);

        while(Sol.size()>0) {
           try {
               Main.cycling(Sol);
               System.out.println(newPlanet.planetStatusUpdate());
           } catch (DeathWorldException e) {
               System.out.println(e.getMessage());
                e.deadPlanet.showChart();
                Sol.remove(e.deadPlanet.getPlanetName());
           }
        }

        System.out.println(newPlanet.planetStatusUpdate());
    }


    /**
     * infests the given planet with life
     * @param planet
     */
    private static void infest(Planet planet) {
        Organism simpleAnimalCell = new AnimalCell(planet);
        Organism simplePlantCell = new PlantCell(planet);
    }

    /**
     * all planet experience one cycle
     * @param solarSystem
     * @throws DeathWorldException planet is dead
     */
    private static void cycling(HashMap<String, Planet> solarSystem) throws DeathWorldException {
        for (Planet planet : solarSystem.values()) {
            planet.cycling();
        }
    }

}
