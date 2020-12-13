package com.eisenglatz;

public class DeathWorldException extends Exception{

    Planet deadPlanet;

    /**
     * determines that an organism starved
     */
    public DeathWorldException(Planet deadPlanet) {
        super();
        this.deadPlanet = deadPlanet;
    }
}
