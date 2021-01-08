package stellar.planet;

import stellar.organism.AnimalCell;
import stellar.organism.Organism;
import stellar.organism.PlantCell;
import stellar.resource.Carbon;
import stellar.resource.CarbonDioxide;
import stellar.resource.Oxygen;
import stellar.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Integer cycleCount = 0;

        PlanetarySystem planetarySystem = new PlanetarySystem();
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(500));
        seed.add(new Carbon(500));
        seed.add(new CarbonDioxide(500));

        while(cycleCount < 5) {
            cycleCount++;
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
        //Organism simplePlantCell = new PlantCell(planet);
        planet.wildLiveAddNewLive();
    }

}
