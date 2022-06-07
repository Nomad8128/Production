package hr.java.production.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Templae to create object of tipe category
 */
public class Category extends NamedEntity implements Serializable {
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Constructor for Category
     * @param name name of the category which will be sent to the constructor of the super class (NamedEntity)
     * @param description description of the category
     */
    public Category(String name, long id, String description) {
        super(name, id);
        //this.name = name;
        this.description = description;
    }
    public Category() {

    }
    public Category(String name) {
        this.name=name;
    }
    public Category(long id, String name, String description) {
        super(id, name);
        //this.name = name;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

}
