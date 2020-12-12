package com.eisenglatz;

import java.util.UUID;

public class Organism  implements ICyclable{
    private Metabolism metabolism;
    private Planet planet;
    private UUID guid;

    /**
     * public constructor
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public Organism(Planet planet) {

        this.planet = planet;
        this.guid = UUID.randomUUID();
        metabolism = new Metabolism(this.planet); // this, um organismus object zu übergeben
        this.planet.lifeReceived(this);
    }

    /**
     * organisms eats, which activates metabolism and printing of new resource amounts
     */
    public void eat() {
        metabolism.transform();

        Resource toLocalCarbonField = planet.getResource("carbon");
        Resource toLocalOxygenField = planet.getResource("oxygen");
        Resource toLocalCarbonDioxideField = planet.getResource("carbonDioxide");

        System.out.println("CarbonAmount: " + toLocalCarbonField.getStock());
        System.out.println("OxygenAmount: " + toLocalOxygenField.getStock());
        System.out.println("CarbonDioxideAmount: " + toLocalCarbonDioxideField.getStock());
    }

    @Override
    public UUID GetUUID() {
        return guid;
    }

    @Override
    public void dayDream() {
    }
}
