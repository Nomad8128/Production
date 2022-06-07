package hr.java.production.genericsi;

import hr.java.production.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * template for creating a store that will only have edible products
 * @param <T> any class that implements the interface Edible
 */
public class FoodStore<T extends Edible> extends Store {
    private List<T> edibleList = new ArrayList<>();


    public FoodStore(List<T> edibleList) {
        this.edibleList = edibleList;
    }

    public FoodStore(){}

    public List<T> getEdibleList() {
        return edibleList;
    }

    public void setEdibleList(List<T> edibleList) {
        this.edibleList = edibleList;
    }
}
