package com.eisenglatz;

import java.util.Map;

public class AnimalCell extends Organism{

    /**
     * public constructor
     *
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public AnimalCell(Planet planet) {
        super(planet);
        super.requiredResource.put(Oxygen.class,4);
        super.requiredResource.put(Carbon.class,2);
        super.producedResource.put(CarbonDioxide.class,2);
    }




}
