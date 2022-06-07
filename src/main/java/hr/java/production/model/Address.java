package hr.java.production.model;
import hr.java.production.enums.Grad;

import java.io.Serializable;
import java.util.Objects;

/**
 * Template to create object of type Address
 */
public class Address implements Serializable {

    private long id;
    private String street;
    private String houseNumber;
    private Grad city;



    /**
     * Constructor for Address which takes object Builder
     * @param builder builder
     */
    private Address(Builder builder) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;

    }

    /**
     * Builder class for address
     */
    public static class Builder{
        private long id;
        private String street;
        private String houseNumber;
        private Grad city;


        public Builder(String street){
            this.street=street;
        }
        public Builder atHouseNumber(String houseNumber){
            this.houseNumber=houseNumber;
            return this;
        }
        public Builder inCity(Grad city){
            this.city=city;
            return this;
        }
        public Builder hasId(long id){
            this.id=id;
            return this;
        }


        /**
         * Builder for address
         * @return returns built address
         */
        public Address build(){
            Address adresa = new Address(this);
            adresa.street=this.street;
            adresa.houseNumber=this.houseNumber;
            adresa.city=this.city;
            adresa.id=this.id;

            return adresa;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(houseNumber, address.houseNumber) && city == address.city;
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, city);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Grad getCity() {
        return city;
    }

    public void setCity(Grad city) {
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return city.getNazivGrada() + ", " + street + ", " + houseNumber;
    }
}
