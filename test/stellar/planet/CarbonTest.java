package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.Carbon;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CarbonTest {
    private Carbon caField;

    private Integer initAmount = 1000;

    @BeforeEach
    void setUp() {
        caField = new Carbon(initAmount);
    }

    @Test
    void getStockTest() {
        Integer receivedAmountOxygen = caField.getStock();
        assertEquals(initAmount, receivedAmountOxygen);
    }

    @Test
    void consumeTest() {
        try {
            Integer amountConsumed = 300;
            Integer receivedAmountOxygen = caField.getStock();
            caField.consume(amountConsumed);

            Integer receivedAmountOxygenNew = caField.getStock();
            Integer amountExpected = receivedAmountOxygen - amountConsumed;
            assertEquals(amountExpected, receivedAmountOxygenNew);
        } catch (Exception ex) {

        }
    }

    @Test
    void addAsyncNewResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        caField.setResourceHandler(newResourceHandler);

        Integer valueToAdd = 10;
        HashMap<Resource, Integer> resourceToAddMap = caField.addAsync(valueToAdd);

        boolean containsCaField = resourceToAddMap.containsKey(caField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(caField);
        assertEquals(valueToAdd, hashMapValue);
    }

    @Test
    void addAsyncAlreadyExistingResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        caField.setResourceHandler(newResourceHandler);

        Integer valueToAdd1 = 10;
        HashMap<Resource, Integer> resourceToAddMap = caField.addAsync(valueToAdd1);

        boolean containsCaField = resourceToAddMap.containsKey(caField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(caField);
        assertEquals(valueToAdd1, hashMapValue);

        Integer valueToAdd2 = 15;
        HashMap<Resource, Integer> resourceToAddMap2 = caField.addAsync(valueToAdd2);

        Integer hashMapSize2 = resourceToAddMap.size();
        assertEquals(1, hashMapSize2);
        Integer hashMapValue2 = resourceToAddMap.get(caField);
        assertEquals(valueToAdd1+valueToAdd2, hashMapValue2);
    }

    @Test
    void getResourceHandlerTest() {
        ResourceHandler originResourceHandler = caField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        caField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = caField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
    }

    @Test
    void setResourceHandlerTest() {
        ResourceHandler originResourceHandler = caField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        caField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = caField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
        assertNotSame(originResourceHandler, newSetResourceHandler);
    }
}