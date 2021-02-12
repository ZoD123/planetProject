package stellar.planet;

import org.junit.jupiter.api.Test;
import stellar.organism.AnimalCell;
import stellar.organism.Organism;
import stellar.resource.Carbon;
import stellar.resource.Oxygen;
import stellar.resource.Resource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PlanetTest {
    private final int resourceStartAmount = 1000;
    private Planet testPlanet;


    /**
     * init test Environment
     */
    public void initPlanet() {
        PlanetarySystem planetarySystem = new PlanetarySystem();
        ArrayList<Resource> seed = new ArrayList<Resource>();
        seed.add(new Oxygen(resourceStartAmount));
        seed.add(new Carbon(resourceStartAmount));

        testPlanet = new Planet(seed, planetarySystem);
    }

    @Test
    void getResourceTest() {
        initPlanet();
        assertNotNull(testPlanet, "expected that value is not null");
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
        testPlanet.lifeKilled(testOrganism);
        testPlanet.cycling();
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
        assertNotNull(testPlanet, "expected that value is not null");
    }
}
