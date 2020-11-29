package com.eisenglatz;

import java.sql.PreparedStatement;
import java.time.format.ResolverStyle;

public class Main {

    public static void main(String[] args) {
        PlanetSeed deus = new PlanetSeed();
        deus.oxygen = 5000;
        deus.carbon = 4000;
        deus.carbonDioxide = 10;

        Planet earth = new Planet(deus);

        Resource toLocalCarbonField = earth.getResource("carbon");
        Resource toLocalOxygenField = earth.getResource("oxygen");
        Resource toLocalCarbonDioxideField = earth.getResource("carbonDioxide");

        System.out.println("CarbonAmount: " + toLocalCarbonField.getStock());
        System.out.println("OxygenAmount: " + toLocalOxygenField.getStock());
        System.out.println("CarbonDioxideAmount: " + toLocalCarbonDioxideField.getStock());

        Organism simpleOrganism = new Organism(earth);
        simpleOrganism.eat();

	// write your code here
    }
}
