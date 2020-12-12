package com.eisenglatz;

import java.sql.PreparedStatement;
import java.time.format.ResolverStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        PlanetSeed deus = createSeed();
        Planet earth = new Planet(deus);


        Organism simpleOrganism = new Organism(earth);
        simpleOrganism.eat();

        // write your code here
    }

    /**
     * creates a planet seed
     *
     * @return the created seed
     */
    private static PlanetSeed createSeed() {
        PlanetSeed seed = new PlanetSeed();
        seed.oxygen = 5000;
        seed.carbon = 4000;
        seed.carbonDioxide = 10;
        return seed;
    }


    private static void infest(Planet planet)
    {

    }

}
