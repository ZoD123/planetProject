package stellar.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.Oxygen;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OxygenTest extends ResourceTest {



    @BeforeEach
    void setUp() {
        resourceField = new Oxygen(initAmount);
    }

    @Test
    void getStockTest() {
        super.getStockTest();
    }

    @Test
    void consumeTest() {
        super.consumeTest();
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