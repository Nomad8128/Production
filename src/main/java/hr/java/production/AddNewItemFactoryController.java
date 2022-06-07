package hr.java.production;

import database.Database;
import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewItemFactoryController {

    private static List<Factory> factoryList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();


    @FXML
    private ComboBox comboBox1;

    @FXML
    private ComboBox comboBox2;

    @FXML
    public void initialize() {
        factoryList.clear();
        itemList.clear();

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            factoryList = Database.getAllFactoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            itemList = Database.getAllItemsFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        ObservableList<Factory> tvorniceObservableList = FXCollections.observableList(factoryList);
        ObservableList<Factory> options =
                FXCollections.observableList(factoryList);
        comboBox1.setItems(options);
        ObservableList<Item> itemsObservableList = FXCollections.observableList(itemList);
        ObservableList<Item> options2 =
                FXCollections.observableList(itemList);
        comboBox2.setItems(options2);


    }
    @FXML
    protected void onDodajButtonClick() {
        if (!comboBox1.getSelectionModel().isEmpty() && !comboBox2.getSelectionModel().isEmpty()){
            Factory tvornica = (Factory)comboBox1.getSelectionModel().getSelectedItem();
            Item item = (Item)comboBox2.getSelectionModel().getSelectedItem();

            try (Connection connection = Database.connectToDatabase()) {
                Database.insertItemIntoFactory(connection, tvornica.getId(), item.getId());
            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }
        }
    }
}
