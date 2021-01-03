package stellar.planet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeExpiredEvaluatorTest {

    private TimeExpiredEvaluator timeExpiredEvaluator;
    private Integer threshold = 700;
    private Integer approximationRange = 1000;


    @BeforeEach
    void initTestEnvirionment() {
        timeExpiredEvaluator = new TimeExpiredEvaluator(approximationRange);
    }


    @Test
    void randomnessOfTimeExpiredEvaluatorIsInRangetest() {
        Integer cycle;
        Integer approximationExpected;
        Integer approximationLowerBoundary;
        Integer approximationUpperBoundary;
        Integer endRange = 10000;

        Integer numberOfFalseReturns = 0;
        boolean testReturn;

        for (cycle = 1; cycle < endRange; cycle++) {
            testReturn = timeExpiredEvaluator.checkLifeIsExpired(threshold);

            if (testReturn == false) {
                numberOfFalseReturns++;
            }
        }

        // (* 1000)  (/ 1000) cause of Integer
        approximationExpected = ((threshold * 1000 / approximationRange) * endRange) / 1000;
        approximationUpperBoundary = approximationExpected + endRange / 10;
        approximationLowerBoundary = approximationExpected - endRange / 10;

        assertTrue(numberOfFalseReturns > approximationLowerBoundary &&
                numberOfFalseReturns < approximationUpperBoundary);
    }
}
