package stellar.planet;

import java.util.HashMap;
import java.util.UUID;

public class PlanetarySystem {

    private final HashMap<UUID, Planet> livingPlanets;
    private final HashMap<UUID, Planet> deadPlanets;

    public PlanetarySystem() {
        livingPlanets = new HashMap<UUID, Planet>();
        deadPlanets = new HashMap<UUID, Planet>();
    }

    public void add(Planet planet) {
        livingPlanets.put(planet.getPlanetID(), planet);
    }

    public void planetDied(Planet planet) {
        UUID planetUUID = planet.getPlanetID();

        if (planet == null) {
            return;
        }

        deadPlanets.put(planetUUID, planet);
        livingPlanets.remove(planetUUID);
    }

    public HashMap<UUID, Planet> getLivingPlanets() {
        return livingPlanets;
    }

    public HashMap<UUID, Planet> getDeadPlanets() {
        return deadPlanets;
    }

    public Integer getLivingPlanetsCount() {
        return livingPlanets.size();
    }

    public Integer getDeadPlanetsCount() {
        return deadPlanets.size();
    }
}
