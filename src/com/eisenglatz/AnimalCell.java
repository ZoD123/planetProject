package com.eisenglatz;

public class AnimalCell extends Organism{
    /**
     * public constructor
     *
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public AnimalCell(Planet planet) {
        super(planet);
    }
}
