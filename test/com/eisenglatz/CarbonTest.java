package com.eisenglatz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(initAmount,receivedAmountOxygen);
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
        } catch(Exception ex) {

        }
    }

    @Test
    void produceTest() {
        try {
            Integer amountProduced = 300;
            Integer receivedAmountOxygen = caField.getStock();
            caField.produce(amountProduced);

            Integer receivedAmountOxygenNew = caField.getStock();
            Integer amountExpected = receivedAmountOxygen + amountProduced;
            assertEquals(amountExpected, receivedAmountOxygenNew);
        } catch(Exception ex) {

        }
    }
}