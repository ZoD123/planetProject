package com.eisenglatz;

public class Organism {
    private Metabolism metabolism;
    private Planet planet;

    /**
     * public constructor
     * @param planet defines the location where the organism lives
     *               and also defines which resources are available when metabolism get initialized
     */
    public Organism(Planet planet) {
        this.planet = planet;
        metabolism = new Metabolism(this.planet); // this, um organismus object zu übergeben
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

}
