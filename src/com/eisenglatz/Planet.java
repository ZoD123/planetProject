package com.eisenglatz;

import java.util.HashMap;
import java.util.UUID;

public class Planet {


    private Carbon carbonField;
    private Oxygen oxygenField;
    private CarbonDioxide carbonDioxideField;

    /**
     *  Represents the information for all living organisms on the planet
     */
    private HashMap<UUID,ICyclable> wildlive;

    /**
     * public constructor
     * @param seed determine the seed which is used to create initial values
     */
    public Planet(PlanetSeed seed) {
        carbonField = new Carbon(seed.carbon);
        oxygenField = new Oxygen(seed.oxygen);
        carbonDioxideField = new CarbonDioxide(seed.carbonDioxide);
        wildlive = new HashMap<UUID, ICyclable>();
    }

    /**
     * delivers (if possible) the requested ressource from the planet
     * @param resourceOfInterest the ressource type the requester want to have
     * @return the requested ressource
     */
    public Resource getResource(String resource){

        if (resource == "carbon") {
            return carbonField;
        }

        if (resource == "oxygen") {
            return oxygenField;
        }

        if (resource == "carbonDioxide") {
            return carbonDioxideField;
        }

        return null;

    }

    /**
     * Planet received a new living organism which is able to live at least one cycle :-)
     * @param object the object which will be living
     */
    public void lifeReceived(ICyclable object) {
        wildlive.put(object.GetUUID(), object);
    }

    /**
     * Kills the no longer living organism from planet
     * @param object which now is dead :-(
     */
    public void lifeKilled(ICyclable object){
        wildlive.remove(object.GetUUID());
    }

}
