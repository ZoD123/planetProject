package com.eisenglatz;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetTest {
    private final int resourceStartAmount = 1000;
    private Planet TestPlanet;


    /**
     * init test Environment
     */
   public void initPlanet() {
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(resourceStartAmount));
        seed.add(new Carbon(resourceStartAmount));

        TestPlanet = new Planet("TestPlanet",seed);
    }

    @Test
    void getResourceTest()
    {
       initPlanet();
       assertNotNull(TestPlanet,"expected that value is not null");
    }

    @Test
    void lifeReceivedTest() {
        initPlanet();
        Integer originWildLifeCount = TestPlanet.getNumberOfLivingOrganism();
        TestPlanet.lifeReceived(new AnimalCell(TestPlanet));
        Integer newWildLifeCount = TestPlanet.getNumberOfLivingOrganism();
        assertEquals(originWildLifeCount + 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void lifeKilledTest() {
        initPlanet();
        Organism testOrganism = new AnimalCell(TestPlanet);
        TestPlanet.lifeReceived(testOrganism);

        Integer originWildLifeCount = TestPlanet.getNumberOfLivingOrganism();
        TestPlanet.lifeKilled(testOrganism);
        Integer newWildLifeCount = TestPlanet.getNumberOfLivingOrganism();
        assertEquals(originWildLifeCount - 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void cyclingTest() {
        // nothing to test....
    }

    @Test
    void getPlanetNameTest() {
        initPlanet();
        assertNotNull(TestPlanet,"expected that value is not null");
    }
}
