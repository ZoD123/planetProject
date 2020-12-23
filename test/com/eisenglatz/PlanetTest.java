package com.eisenglatz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlanetTest {
    private final int ressourceStartAmount = 1000;
    private Planet TestPlanet;
    private PlanetSeed TestSeed;

    @BeforeAll
   public void initPlanet() {
        TestSeed = new PlanetSeed();
        TestSeed.carbon = ressourceStartAmount;
        TestSeed.oxygen = ressourceStartAmount;

        TestPlanet = new Planet("TestPlanet",TestSeed);
    }

    @Test
    void getResource() {
    assertEquals(ressourceStartAmount, TestPlanet.getResource("oxygen"),"different ammount for ressource than expected");
    }

    @Test
    void lifeReceived() {
    }

    @Test
    void lifeKilled() {
    }

    @Test
    void cycling() {
    }

    @Test
    void getPlanetName() {
    }

    @Test
    public void

}
