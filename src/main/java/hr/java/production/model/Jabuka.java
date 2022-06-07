package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Template for object type Jabuka which extends Item
 */
public class Jabuka extends Item implements Edible, Serializable {
    static int kaloriji_po_kilogramu = 521;
    BigDecimal weight;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Jabuka)) return false;
        if (!super.equals(o)) return false;
        Jabuka jabuka = (Jabuka) o;
        return Objects.equals(weight, jabuka.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }

    /**
     * Constructor for jabuka
     * @param name name for jabuka
     * @param category category for jabuka
     * @param width width for jabuka
     * @param height height for jabuka
     * @param length length for jabuka
     * @param productionCost production cost for jabuka
     * @param sellingPrice selling price for jabuka
     * @param popust discount for jabuka
     * @param weight weight of jabuka
     */
    public Jabuka(String name, long id, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, BigDecimal weight){
        super(name, id, category, width, height, length, productionCost, sellingPrice, popust);
        this.weight = weight;
    }
    public Jabuka(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, BigDecimal weight){
        super(id, name, category, width, height, length, productionCost, sellingPrice, popust);
        this.weight = weight;
    }

    /**
     * Defaul constructor of jabuka
     */
    public Jabuka(){};

    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * Returns number of calories per kg
     * @return returns number of calories per kg
     */
    @Override
    public int calculateKilocalories() {
        return kaloriji_po_kilogramu;
    }

    /**
     * Calculates the price based on weight
     * @return returns the price
     */
    @Override
    public BigDecimal calculatePrice() {
        return (this.weight.multiply(this.getSellingPrice()));
    }
}
