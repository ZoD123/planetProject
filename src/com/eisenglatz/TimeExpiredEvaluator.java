package com.eisenglatz;
import java.util.Random;

/**
 * class represents an expirering evaluator which determines that the live of an organism on the planet is
 * expired (sad....)
 */
public class TimeExpiredEvaluator {
    private Integer range;
    private Random randomGenerator;

    /**
     * public constructor
     * @param range determines the upper boundery for the approximation range (from 1 to range)
     */
    public TimeExpiredEvaluator(Integer range){
        this.range = range;
        randomGenerator = new Random();
    }


    /**
     * method checks if the time for the organism is expired based on the initialized range
     * @param threshold aproximation which has to be reached.
     * @return
     */
    public boolean checkLifeIsExpired(Integer threshold){
        Integer randomNumber = randomGenerator.nextInt(range);

        if(randomNumber >= threshold) {
            return true;
        }
        return false;
    }
}
