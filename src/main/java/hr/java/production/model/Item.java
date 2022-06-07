package hr.java.production.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Template for creating an object of type Item
 */
public class Item extends NamedEntity implements Serializable {
    //private String name;
    private long categoryID;
    private Category category;
    private BigDecimal width;
    private BigDecimal height;
    private BigDecimal length;
    private BigDecimal productionCost;
    private BigDecimal sellingPrice;
    private Discount popust;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return Objects.equals(category, item.category) && Objects.equals(width, item.width) && Objects.equals(height, item.height) && Objects.equals(length, item.length) && Objects.equals(productionCost, item.productionCost) && Objects.equals(sellingPrice, item.sellingPrice) && Objects.equals(popust, item.popust);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, width, height, length, productionCost, sellingPrice, popust);
    }

    /**
     * Constructor for item
     * @param name name of the item
     * @param category category of the item
     * @param width width of the item
     * @param height height of the item
     * @param length length of the item
     * @param productionCost production cost of the item
     * @param sellingPrice selling price of the item
     * @param broj discount for the item
     */
    public Item(String name,Long id, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal broj) {
        super(name, id);
        //this.name = name;
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.popust= new Discount(broj);
    }

    public Item(Long id, String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, BigDecimal broj) {
        super(id, name);
        //this.name = name;
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.popust= new Discount(broj);
    }

    public Item(String name, Category category, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice) {
        super(name);
        //this.name = name;
        this.category = category;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;

    }

    public Item(Long id, Long categoryID, String name, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice) {
        super(id, name);
        //this.name = name;
        this.categoryID = categoryID;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
    }

    public Item(Long id, Long categoryID, String name, BigDecimal width, BigDecimal height, BigDecimal length, BigDecimal productionCost, BigDecimal sellingPrice, Category category) {
        super(id, name);
        //this.name = name;
        this.categoryID = categoryID;
        this.width = width;
        this.height = height;
        this.length = length;
        this.productionCost = productionCost;
        this.sellingPrice = sellingPrice;
        this.category = category;
    }

    /**
     * Default constructor for item
     */
    public Item(){};

    /**
     * Construct for item only with name
     * @param name name of the item
     */
    public Item(String name){
        super(name);
    }

    public Discount getPopust() {
        return popust;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(BigDecimal productionCost) {
        this.productionCost = productionCost;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return name;
    }
}
