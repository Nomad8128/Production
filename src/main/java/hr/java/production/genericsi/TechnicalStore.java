package hr.java.production.genericsi;


import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.ArrayList;
import java.util.List;

/**
 * class for creating an object of type Technical store
 * @param <T> any object that implements the interface Technical
 */
public class TechnicalStore<T extends Technical> extends Store {
    private List<T> technicalList = new ArrayList<>();

    public TechnicalStore(List<T> technicalList){
        this.technicalList=technicalList;
    }

    public TechnicalStore(){}

    public List<T> getTechnicalList() {
        return technicalList;
    }

    public void setTechnicalList(List<T> technicalList) {
        this.technicalList = technicalList;
    }
}
