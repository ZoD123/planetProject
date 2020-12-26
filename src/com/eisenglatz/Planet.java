package com.eisenglatz;

import java.util.HashMap;
import java.util.UUID;

public class Planet {

    private String planetName;
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
    public Planet(String PlanetName, PlanetSeed seed) {
        this.planetName = PlanetName;
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
    public Resource getResource(Class requestedResource){

        if(requestedResource == Carbon.class){
            return carbonField;
        }

        if (requestedResource == Oxygen.class){
            return oxygenField;
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

    /**
     * World experience one cycle
     * @throws DeathWorldException world now is dead :-(
     */
    public void cycling() throws DeathWorldException {
        String output;

        for ( ICyclable element: wildlive.values() )  {
            element.dayDream();
        }
        output = "C: " + carbonField.getStock() + " | ";
        output += "O: " + oxygenField.getStock() + " | ";
        output += "CO2: " + carbonDioxideField.getStock() + " | ";
        System.out.println(output);

        if(wildlive.size() < 1) {
            throw new DeathWorldException(this);
        }

    }

    /**
     * returns the name of the planet
     * @return name of the planet
     */
    public String getPlanetName() {
        return planetName;
    }

    /**
     * returns the number of current living organisms.
     * @return number of living organisms.
     */
    public Integer GetNumberOfLivingOrganism() {
        if(wildlive == null)
        {
            throw new NullPointerException();
        }

        return wildlive.size();
    }
}
