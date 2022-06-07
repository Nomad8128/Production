package hr.java.production.sort;

import hr.java.production.model.Item;
import java.util.Comparator;

/**
 * This class overrides the compare method so it can be used for sorting items based on their selling price (both ascending and descending)
 */
public class ProductionSorter implements Comparator<Item> {

    @Override
    public int compare(Item a, Item b){
        if (a.getSellingPrice().compareTo(b.getSellingPrice()) > 0){
            return 1;
        }
        else if (a.getSellingPrice().compareTo(b.getSellingPrice()) < 0){
            return -1;
        }
        else{
            return a.getName().compareTo(b.getName());
        }
    }
}
