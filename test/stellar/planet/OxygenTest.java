package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.Oxygen;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenTest {
    private Oxygen oxField;

    private Integer initAmount = 1000;

    @BeforeEach
    void setUp() {
        oxField = new Oxygen(initAmount);
    }

    @Test
    void getStockTest() {
        Integer receivedAmountOxygen = oxField.getStock();
        assertEquals(initAmount, receivedAmountOxygen);
    }

    @Test
    void consumeTest() {
        try {
            Integer amountConsumed = 300;
            Integer receivedAmountOxygen = oxField.getStock();
            oxField.consume(amountConsumed);

            Integer receivedAmountOxygenNew = oxField.getStock();
            Integer amountExpected = receivedAmountOxygen - amountConsumed;
            assertEquals(amountExpected, receivedAmountOxygenNew);
        } catch (Exception ex) {

        }
    }

    @Test
    void addAsyncNewResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        oxField.setResourceHandler(newResourceHandler);

        Integer valueToAdd = 10;
        HashMap<Resource, Integer> resourceToAddMap = oxField.addAsync(valueToAdd);

        boolean containsCaField = resourceToAddMap.containsKey(oxField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(oxField);
        assertEquals(valueToAdd, hashMapValue);
    }

    @Test
    void addAsyncAlreadyExistingResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        oxField.setResourceHandler(newResourceHandler);

        Integer valueToAdd1 = 10;
        HashMap<Resource, Integer> resourceToAddMap = oxField.addAsync(valueToAdd1);

        boolean containsCaField = resourceToAddMap.containsKey(oxField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(oxField);
        assertEquals(valueToAdd1, hashMapValue);

        Integer valueToAdd2 = 15;
        HashMap<Resource, Integer> resourceToAddMap2 = oxField.addAsync(valueToAdd2);

        Integer hashMapSize2 = resourceToAddMap.size();
        assertEquals(1, hashMapSize2);
        Integer hashMapValue2 = resourceToAddMap.get(oxField);
        assertEquals(valueToAdd1+valueToAdd2, hashMapValue2);
    }

    @Test
    void getResourceHandlerTest() {
        ResourceHandler originResourceHandler = oxField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        oxField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = oxField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
    }

    @Test
    void setResourceHandlerTest() {
        ResourceHandler originResourceHandler = oxField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        oxField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = oxField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
        assertNotSame(originResourceHandler, newSetResourceHandler);
    }
}