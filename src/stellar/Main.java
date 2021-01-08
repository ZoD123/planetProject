package stellar;

import stellar.organism.AnimalCell;
import stellar.organism.PlantCell;
import stellar.organism.Organism;
import stellar.planet.Planet;
import stellar.planet.PlanetarySystem;
import stellar.resource.Carbon;
import stellar.resource.CarbonDioxide;
import stellar.resource.Oxygen;
import stellar.resource.Resource;


import java.util.ArrayList;

public class Main {


    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer cycleCount = 0;
        Integer maxCycles = 3;

        PlanetarySystem planetarySystem = new PlanetarySystem();

        while (cycleCount < maxCycles) {
            cycleCount++;
            ArrayList<Resource> seed = new ArrayList<Resource>();
            seed.add(new Oxygen(500));
            seed.add(new Carbon(500));
            seed.add(new CarbonDioxide(500));
            Planet newPlanet = new Planet(seed, planetarySystem);
            planetarySystem.add(newPlanet);
            infest(newPlanet);
            newPlanet.start();
        }

        System.out.println("Simulierte Planeten: " + planetarySystem.getDeadPlanetsCount());

    }


    /**
     * infests the given planet with life
     *
     * @param planet
     */
    private static void infest(Planet planet) {
        Organism simpleAnimalCell = new AnimalCell(planet);
        Organism simplePlantCell = new PlantCell(planet);
        planet.wildLiveAddNewLive();
    }

}
