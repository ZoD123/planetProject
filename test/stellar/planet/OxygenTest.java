package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.Oxygen;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//    @Test
//    void produceTest() {
//        try {
//            Integer amountProduced = 300;
//            Integer receivedAmountOxygen = oxField.getStock();
//            oxField.produce(amountProduced);
//
//            Integer receivedAmountOxygenNew = oxField.getStock();
//            Integer amountExpected = receivedAmountOxygen + amountProduced;
//            assertEquals(amountExpected, receivedAmountOxygenNew);
//        } catch (Exception ex) {
//
//        }
//    }
}