package stellar.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ResourceTest {

    private Resource resourceField;

    private Integer initAmount = 1000;

    @BeforeEach
    void setUp() {
        resourceField = new Carbon(initAmount);
    }

    @Test
    void getStockTest() {
        Integer receivedAmountResource = resourceField.getStock();
        assertEquals(initAmount, receivedAmountResource);
    }

    @Test
    void consumeTest() {
        try {
            Integer amountConsumed = 300;
            Integer receivedAmountResource = resourceField.getStock();
            resourceField.consume(amountConsumed);

            Integer receivedAmountOxygenNew = resourceField.getStock();
            Integer amountExpected = receivedAmountResource - amountConsumed;
            assertEquals(amountExpected, receivedAmountOxygenNew);
        } catch (Exception ex) {

        }
    }

    @Test
    void addAsyncNewResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        resourceField.setResourceHandler(newResourceHandler);

        Integer valueToAdd = 10;
        HashMap<Resource, Integer> resourceToAddMap = resourceField.addAsync(valueToAdd);

        boolean containsCaField = resourceToAddMap.containsKey(resourceField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(resourceField);
        assertEquals(valueToAdd, hashMapValue);
    }

    @Test
    void addAsyncAlreadyExistingResourceTest() {
        ResourceHandler newResourceHandler = new ResourceHandler();
        resourceField.setResourceHandler(newResourceHandler);

        Integer valueToAdd1 = 10;
        HashMap<Resource, Integer> resourceToAddMap = resourceField.addAsync(valueToAdd1);

        boolean containsCaField = resourceToAddMap.containsKey(resourceField);
        assertTrue(containsCaField);
        Integer hashMapSize = resourceToAddMap.size();
        assertEquals(1, hashMapSize);
        Integer hashMapValue = resourceToAddMap.get(resourceField);
        assertEquals(valueToAdd1, hashMapValue);

        Integer valueToAdd2 = 15;
        HashMap<Resource, Integer> resourceToAddMap2 = resourceField.addAsync(valueToAdd2);

        Integer hashMapSize2 = resourceToAddMap.size();
        assertEquals(1, hashMapSize2);
        Integer hashMapValue2 = resourceToAddMap.get(resourceField);
        assertEquals(valueToAdd1+valueToAdd2, hashMapValue2);
    }

    @Test
    void getResourceHandlerTest() {
        ResourceHandler originResourceHandler = resourceField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        resourceField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = resourceField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
    }

    @Test
    void setResourceHandlerTest() {
        ResourceHandler originResourceHandler = resourceField.getResourceHandler();
        ResourceHandler newResourceHandler = new ResourceHandler();
        resourceField.setResourceHandler(newResourceHandler);
        ResourceHandler newSetResourceHandler = resourceField.getResourceHandler();

        assertSame(newResourceHandler, newSetResourceHandler);
        assertNotSame(originResourceHandler, newSetResourceHandler);
    }

}