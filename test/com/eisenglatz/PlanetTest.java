package com.eisenglatz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetTest {
    private final int ressourceStartAmount = 1000;
    private Planet TestPlanet;
    private PlanetSeed TestSeed;

    /**
     * init test Environment
     */
   public void initPlanet() {
        TestSeed = new PlanetSeed();
        TestSeed.carbon = ressourceStartAmount;
        TestSeed.oxygen = ressourceStartAmount;

        TestPlanet = new Planet("TestPlanet",TestSeed);
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
        Integer originWildLifeCount = TestPlanet.GetNumberOfLivingOrganism();
        TestPlanet.lifeReceived(new Organism(TestPlanet));
        Integer newWildLifeCount = TestPlanet.GetNumberOfLivingOrganism();
        assertEquals(originWildLifeCount + 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void lifeKilledTest() {
        initPlanet();
        Organism testOrganism = new Organism(TestPlanet);
        TestPlanet.lifeReceived(testOrganism);

        Integer originWildLifeCount = TestPlanet.GetNumberOfLivingOrganism();
        TestPlanet.lifeKilled(testOrganism);
        Integer newWildLifeCount = TestPlanet.GetNumberOfLivingOrganism();
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
