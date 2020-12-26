package com.eisenglatz;

import java.sql.PreparedStatement;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        Integer cycleCount = 0;

        PlanetSeed deus = createSeed();
        HashMap<String, Planet> Sol = new HashMap<String, Planet>();
        Planet newPlanet = new Planet("Earth", deus);
        Sol.put(newPlanet.getPlanetName(), newPlanet);

        infest(newPlanet);

        while(Sol.size()>0) {
            cycleCount++;
           try {
               Main.cycling(Sol);
           } catch (DeathWorldException e) {
                Sol.remove(e.deadPlanet.getPlanetName());
           }
        }
        System.out.println("World is starved after " + cycleCount + " cycles");
    }

    /**
     * creates a planet seed
     *
     * @return the created seed
     */
    private static PlanetSeed createSeed() {
        PlanetSeed seed = new PlanetSeed();
        seed.oxygen = 5000;
        seed.carbon = 4000;
        seed.carbonDioxide = 10;
        return seed;
    }

    /**
     * infests the given planet with life
     * @param planet
     */
    private static void infest(Planet planet)
    {
        Organism simpleOrganism = new AnimalCell(planet);
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
