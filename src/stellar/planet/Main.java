package stellar.planet;

import stellar.organism.AnimalCell;
import stellar.organism.Organism;
import stellar.organism.PlantCell;
import stellar.resource.Carbon;
import stellar.resource.CarbonDioxide;
import stellar.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    private ChartModule chartModule;

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(500));
        seed.add(new Carbon(500));
        seed.add(new CarbonDioxide(500));
        Integer cycleCount = 0;
        HashMap<String, Planet> Sol = new HashMap<String, Planet>();
        Planet newPlanet = new Planet("Earth", seed);
        Sol.put(newPlanet.getPlanetName(), newPlanet);

        infest(newPlanet);

        while (Sol.size() > 0) {
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
     *
     * @param planet
     */
    private static void infest(Planet planet) {
        Organism simpleAnimalCell = new AnimalCell(planet);
        Organism simplePlantCell = new PlantCell(planet);
        planet.wildLiveAddNewLive();
    }

    /**
     * all planet experience one cycle
     *
     * @param solarSystem
     * @throws DeathWorldException planet is dead
     */
    private static void cycling(HashMap<String, Planet> solarSystem) throws DeathWorldException {
        for (Planet planet : solarSystem.values()) {
            planet.cycling();
        }
    }

}
