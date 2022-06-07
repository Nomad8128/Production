package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Template for creating an object of type Laptop
 */
public final class Laptop extends Item implements Technical, Serializable {
    private int warranty;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Laptop)) return false;
        if (!super.equals(o)) return false;
        Laptop laptop = (Laptop) o;
        return warranty == laptop.warranty;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), warranty);
    }

    /**
     * Constructor for laptop
     * @param name name of laptop
     * @param category category of laptop
     * @param width width of laptop
     * @param height height of laptop
     * @param length length of laptop
     * @param productionCost production cost of laptop
     * @param sellingPrice selling price of laptop
     * @param popust discount for laptop
     * @param warranty warranty of laptop
     */
    public Laptop(String name, long id, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, int warranty){
        super(name, id, category, width, height, length, productionCost, sellingPrice, popust);
        this.warranty = warranty;
    }
    public Laptop(long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal popust, int warranty){
        super(id, name, category, width, height, length, productionCost, sellingPrice, popust);
        this.warranty = warranty;
    }

    /**
     * Returns the length of the warranty
     * @return length of warranty
     */
    @Override
    public int garancija(){
        return warranty;
    }
}
