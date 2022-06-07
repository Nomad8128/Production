package hr.java.production.model;

import hr.java.production.enums.Grad;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Template for creating an object of type Factory
 */
public class Factory extends NamedEntity implements Serializable {
    private Address address;
    //private Item[] items;
    private Set<Item> items = new HashSet<Item>();
    private int addressId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factory)) return false;
        Factory factory = (Factory) o;
        return Objects.equals(address, factory.address) && Objects.equals(items, factory.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, items);
    }

    /**
     * Constructor for factory with an address
     * @param name name of the factory
     * @param address address of the factory
     * @param items array of items for the factory
     */
    public Factory(String name, Address address, Set<Item> items) {
        super(name);
        this.address = address;
        this.items = items;
    }

    /**
     * Constructor for the factory without an address
     * @param name name of the factory
     * @param items array of items for the factory
     */
    public Factory(String name, long id, Set<Item> items) {
        super(name, id);
        Address example = new Address.Builder("Default").atHouseNumber("Default").inCity(Grad.Default).build();
        this.address = example;
        this.items = items;
    }
    public Factory(long id, String name, Set<Item> items) {
        super(id, name);
        Address example = new Address.Builder("Default").atHouseNumber("Default").inCity(Grad.Default).build();
        this.address = example;
        this.items = items;
    }
    public Factory(long id, String name, Address address, Set<Item> items) {
        super(id, name);
        this.address = address;
        this.items = items;
    }
    public Factory(long id, String name, Address address){
        super(id, name);
        this.address = address;
    }
    public Factory(long id, String name, Address address, int addressId){
        super(id, name);
        this.address = address;
        this.addressId=addressId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
