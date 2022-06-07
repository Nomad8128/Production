package hr.java.production;

import database.Database;
import hr.java.production.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewItemStoreController {

    private static List<Store> storeList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();


    @FXML
    private ComboBox comboBox1A;

    @FXML
    private ComboBox comboBox2A;

    @FXML
    public void initialize() {
        storeList.clear();
        itemList.clear();

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            storeList = Database.getAllStoresFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            itemList = Database.getAllItemsFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        ObservableList<Store> ducaniObservableList = FXCollections.observableList(storeList);
        ObservableList<Store> options =
                FXCollections.observableList(storeList);
        comboBox1A.setItems(options);
        ObservableList<Item> itemsObservableList = FXCollections.observableList(itemList);
        ObservableList<Item> options2 =
                FXCollections.observableList(itemList);
        comboBox2A.setItems(options2);


    }
    @FXML
    protected void onDodajButtonClick() {
        if (!comboBox1A.getSelectionModel().isEmpty() && !comboBox2A.getSelectionModel().isEmpty()){
            Store ducan = (Store)comboBox1A.getSelectionModel().getSelectedItem();
            Item item = (Item)comboBox2A.getSelectionModel().getSelectedItem();

            try (Connection connection = Database.connectToDatabase()) {
                Database.insertItemIntoStore(connection, ducan.getId(), item.getId());
            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }
        }
    }
}
