package com.eisenglatz;

public class Planet {


    private Carbon carbonField;
    private Oxygen oxygenField;
    private CarbonDioxide carbonDioxideField;

    /**
     * public constructor
     * @param seed determine the seed which is used to create initial values
     */
    public Planet(PlanetSeed seed) {
        carbonField = new Carbon(seed.carbon);
        oxygenField = new Oxygen(seed.oxygen);
        carbonDioxideField = new CarbonDioxide(seed.carbonDioxide);
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

}
