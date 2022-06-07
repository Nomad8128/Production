package hr.java.production.enums;

import java.io.Serializable;

/**
 * This class has enum variables of cities that contain 2 strings, one for the name of the city, the secon for its postal code
 */
public enum Grad implements Serializable {
    Zagreb("Zagreb", "10000"),
    Daruvar("Daruvar", "43500"),
    Split("Split", "21000"),
    Koprivnica("Koprivnica", "48000"),
    Slavonski_Brod("Slavonski Brod", "35000"),
    Default("Default", "Default");



    private String nazivGrada;
    private String postanskiBroj;

    @Override
    public String toString() {
        return nazivGrada;
    }

    Grad(String nazivGrada, String postanskiBroj) {
        this.nazivGrada = nazivGrada;
        this.postanskiBroj = postanskiBroj;
    }

    public String getNazivGrada() {
        return nazivGrada;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setNazivGrada(String nazivGrada) {
        this.nazivGrada = nazivGrada;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
}
