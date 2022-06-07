package hr.java.production.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Template for creating an object of type Store
 */
public class Store extends NamedEntity implements Serializable {

    private String webAddress;
    //private Item[] items;
    private Set<Item> items = new HashSet<Item>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store store = (Store) o;
        return Objects.equals(webAddress, store.webAddress) && Objects.equals(items, store.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(webAddress, items);
    }

    /**
     * Default constructor for Store
     */
    public Store(){};

    public Store(String name){
        this.name=name;
    };

    public Store(Long id, String name, String webAddress){
        super(id, name);
        this.webAddress=webAddress;
    };

    /**
     * Constructor for Store with an address
     * @param name name of the store
     * @param webAddress address of the store
     * @param items array of items in the store
     */
    public Store(String name, String webAddress, Set<Item> items) {
        super(name);
        this.webAddress = webAddress;
        this.items = items;
    }

    /**
     * Constructor for store without an address
     * @param name name of store
     * @param items array of items in the store
     */
    public Store(String name, long id, Set<Item> items){
        super(name, id);
        this.webAddress = "Default";
        this.items = items;
    }
    public Store(long id, String name, Set<Item> items){
        super(id, name);
        this.webAddress = "Default";
        this.items = items;
    }


    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return name;
    }
}
