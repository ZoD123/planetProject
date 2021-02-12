package stellar.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.CarbonDioxide;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CarbonDioxideTest {
    private CarbonDioxide coField;

    private Integer initAmount = 1000;

    @BeforeEach
    void setUp() {
        coField = new CarbonDioxide(initAmount);
    }

    @Test
    void getStockTest() {
        Integer receivedAmountOxygen = coField.getStock();
        assertEquals(initAmount, receivedAmountOxygen);
    }

    @Test
    void consumeTest() {
        try {
            Integer amountConsumed = 300;
            Integer receivedAmountOxygen = coField.getStock();
            coField.consume(amountConsumed);

            Integer receivedAmountOxygenNew = coField.getStock();
            Integer amountExpected = receivedAmountOxygen - amountConsumed;
            assertEquals(amountExpected, receivedAmountOxygenNew);
        } catch (Exception ex) {

        }
    }

    @Test
    void addAsyncNewResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        coField.setResourceHandler(newResourceHandler);

        Integer valueToAdd = 10;
        HashMap<Resource, Integer> resourceToAddMap = coField.addAsync(valueToAdd);

        boolean containsCaField = resourceToAddMap.containsKey(coField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(coField);
        assertEquals(valueToAdd, hashMapValue);
    }

    @Test
    void addAsyncAlreadyExistingResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        coField.setResourceHandler(newResourceHandler);

        Integer valueToAdd1 = 10;
        HashMap<Resource, Integer> resourceToAddMap = coField.addAsync(valueToAdd1);

        boolean containsCaField = resourceToAddMap.containsKey(coField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(coField);
        assertEquals(valueToAdd1, hashMapValue);

        Integer valueToAdd2 = 15;
        HashMap<Resource, Integer> resourceToAddMap2 = coField.addAsync(valueToAdd2);

        Integer hashMapSize2 = resourceToAddMap.size();
        assertEquals(1, hashMapSize2);
        Integer hashMapValue2 = resourceToAddMap.get(coField);
        assertEquals(valueToAdd1+valueToAdd2, hashMapValue2);
    }

    @Test
    void getResourceHandlerTest() {
        ResourceHandler originResourceHandler = coField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        coField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = coField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
    }

    @Test
    void setResourceHandlerTest() {
        ResourceHandler originResourceHandler = coField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        coField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = coField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
        assertNotSame(originResourceHandler, newSetResourceHandler);
    }
}