package com.eisenglatz;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetTest {
    private final int resourceStartAmount = 1000;
    private Planet testPlanet;


    /**
     * init test Environment
     */
   public void initPlanet() {
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(resourceStartAmount));
        seed.add(new Carbon(resourceStartAmount));

        testPlanet = new Planet("TestPlanet",seed);
    }

    @Test
    void getResourceTest()
    {
       initPlanet();
       assertNotNull(testPlanet,"expected that value is not null");
    }

    @Test
    void lifeReceivedTest() {
        initPlanet();
        Integer originWildLifeCount = testPlanet.getNumberOfLivingOrganism();
        testPlanet.lifeReceived(new AnimalCell(testPlanet));
        Integer newWildLifeCount = testPlanet.getNumberOfLivingOrganism();
        assertEquals(originWildLifeCount + 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void lifeKilledTest() {
        initPlanet();
        Organism testOrganism = new AnimalCell(testPlanet);
        testPlanet.lifeReceived(testOrganism);

        Integer originWildLifeCount = testPlanet.getNumberOfLivingOrganism();
        try {
            testPlanet.lifeKilled(testOrganism);
            testPlanet.cycling();
        } catch(DeathWorldException ex) {

        }
        Integer newWildLifeCount = testPlanet.getNumberOfLivingOrganism();
        assertEquals(originWildLifeCount - 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void cyclingTest() {
        // nothing to test....
    }

    @Test
    void getPlanetNameTest() {
        initPlanet();
        assertNotNull(testPlanet,"expected that value is not null");
    }
}
