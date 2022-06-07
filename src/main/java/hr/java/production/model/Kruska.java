package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Template for creating an object of type Kruska
 */
public class Kruska extends Item implements Edible, Serializable {
    static int kaloriji_po_kilogramu = 570;
    BigDecimal weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Kruska)) return false;
        if (!super.equals(o)) return false;
        Kruska kruska = (Kruska) o;
        return Objects.equals(weight, kruska.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }

    /**
     * Constructor for Kruska
     * @param name name for kruska
     * @param category category for kruska
     * @param width width for kruska
     * @param height height for kruska
     * @param length length for kruska
     * @param productionCost production cost of for kruska
     * @param sellingPrice selling price of for kruska
     * @param popust discount for for kruska
     * @param weight weight of for kruska
     */
    public Kruska(String name, long id, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, BigDecimal weight){
        super(name, id, category, width, height, length, productionCost, sellingPrice, popust);
        this.weight = weight;
    }

    public Kruska(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, BigDecimal weight){
        super(id, name, category, width, height, length, productionCost, sellingPrice, popust);
        this.weight = weight;
    }

    /**
     * Default constructor for Kruska
     */
    public Kruska(){};

    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Returns the number of calories per kg
     * @return number of calories per kg
     */
    @Override
    public int calculateKilocalories() {
        return kaloriji_po_kilogramu;
    }

    /**
     * Returns the price of kruska based on weight
     * @return returns the price
     */
    @Override
    public BigDecimal calculatePrice() {
        return (this.weight.multiply(this.getSellingPrice()));
    }
}
