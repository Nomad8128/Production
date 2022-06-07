package hr.java.production.model;

import java.math.BigDecimal;

/**
 * Interface which has methods "edible" articles will have
 */
public interface Edible {
    /**
     *  Is used to calculate calories based on weight
     * @return int returns the number of calories
     */
    public int calculateKilocalories();

    /**
     * Used for calculating price based on weight
     * @return returns the price after calculations
     */
    public BigDecimal calculatePrice();
}
