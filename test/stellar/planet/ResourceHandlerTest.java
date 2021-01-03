package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.Carbon;
import stellar.resource.Resource;
import stellar.resource.ResourceHandler;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceHandlerTest {
    private ResourceHandler resourceTestHandler;
    private Integer initAmount;

    @BeforeEach
    void setUp() {
        resourceTestHandler = new ResourceHandler();
        initAmount = 100;
    }

    @Test
    void addResourceAlreadyExistingResourceTest() {
        resourceTestHandler.addResource(new Oxygen(initAmount));
        Integer originCount = resourceTestHandler.getFieldSize(Oxygen.class);
        resourceTestHandler.addResource(new Oxygen(initAmount));
        Integer newCount = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(originCount + 1, newCount);

        resourceTestHandler.addResource(new Carbon(initAmount));
        Integer newCount2 = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(newCount, newCount2);
    }

    @Test
    void addResourceNewResourceTest() {
        Integer originCount = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(0, originCount);

        resourceTestHandler.addResource(new Oxygen(initAmount));
        Integer newCount = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(originCount + 1, newCount);
    }

    @Test
    void getResourceClassNotExistingTest() {
        Resource testResource = resourceTestHandler.getResource(Oxygen.class, initAmount);
        assertNull(testResource);
    }

    @Test
    void getResourceMinAmountNotExistingTest() {
        Integer originCount = resourceTestHandler.getFieldSize(Oxygen.class);
        resourceTestHandler.addResource(new Oxygen(initAmount));
        Integer newCount = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(originCount + 1, newCount);

        Resource testResource = resourceTestHandler.getResource(Oxygen.class, initAmount + 1);
        assertNull(testResource);
    }

    @Test
    void getResourceMinAmountExistingTest() {
        Integer originCount = resourceTestHandler.getFieldSize(Oxygen.class);
        Oxygen oxygenField = new Oxygen(initAmount);
        resourceTestHandler.addResource(oxygenField);
        Integer newCount = resourceTestHandler.getFieldSize(Oxygen.class);
        assertEquals(originCount + 1, newCount);

        Resource testResource = resourceTestHandler.getResource(Oxygen.class, initAmount);
        assertSame(oxygenField, testResource);

        Resource testResource2 = resourceTestHandler.getResource(Oxygen.class, initAmount - 1);
        assertSame(oxygenField, testResource2);
    }
}
