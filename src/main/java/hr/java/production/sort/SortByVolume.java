package hr.java.production.sort;

import hr.java.production.model.Item;
import java.util.Comparator;

/**
 * This class overrides the compare method so it can be used for sorting items based on their selling price (both ascending and descending)
 */
public class SortByVolume implements Comparator<Item> {

    @Override
    public int compare(Item i1, Item i2){
        if (i1.getWidth().multiply(i1.getHeight()).multiply(i1.getLength()).compareTo(i2.getWidth().multiply(i2.getHeight()).multiply(i2.getLength()))>0){
            return 1;
        }
        else if (i1.getWidth().multiply(i1.getHeight()).multiply(i1.getLength()).compareTo(i2.getWidth().multiply(i2.getHeight()).multiply(i2.getLength()))<0){
            return -1;
        }
        else{
            return 0;
        }
    }
}
