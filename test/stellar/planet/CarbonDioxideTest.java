package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import stellar.resource.CarbonDioxide;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

//    @Test
//    void addValueTest() {
//        try {
//            Integer amountProduced = 300;
//            Integer receivedAmountOxygen = coField.getStock();
//            coField.addValue(amountProduced);
//
//            Integer receivedAmountOxygenNew = coField.getStock();
//            Integer amountExpected = receivedAmountOxygen + amountProduced;
//            assertEquals(amountExpected, receivedAmountOxygenNew);
//        } catch (Exception ex) {
//
//        }
//    }
}