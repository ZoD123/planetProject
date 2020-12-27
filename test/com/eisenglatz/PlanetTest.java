package com.eisenglatz;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlanetTest {
    private final int resourceStartAmount = 1000;
    private Planet TestPlanet;
    private PlanetSeed TestSeed;

    /**
     * init test Environment
     */
   public void initPlanet() {
        TestSeed = new PlanetSeed();
        TestSeed.carbon = resourceStartAmount;
        TestSeed.oxygen = resourceStartAmount;

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
        TestPlanet.lifeReceived(new AnimalCell(TestPlanet));
        Integer newWildLifeCount = TestPlanet.GetNumberOfLivingOrganism();
        assertEquals(originWildLifeCount + 1, newWildLifeCount, "fail - wrong count");
    }

    @Test
    void lifeKilledTest() {
        initPlanet();
        Organism testOrganism = new AnimalCell(TestPlanet);
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
