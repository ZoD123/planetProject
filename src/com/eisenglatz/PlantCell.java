package com.eisenglatz;

public class PlantCell extends Organism{

    /**
     * public constructor
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public PlantCell(Planet planet) {
        super(planet);
        super.requiredResource.put(CarbonDioxide.class,1);
        super.producedResource.put(Oxygen.class,2);
        super.producedResource.put(Carbon.class,1);
    }

    @Override
    protected void reproduce() {
        new PlantCell(super.planet);
        super.reproductionThreshold = 0;
    }
}
