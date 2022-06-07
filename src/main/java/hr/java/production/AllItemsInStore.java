package hr.java.production;

import database.Database;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import hr.java.production.model.Store;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllItemsInStore {

    private static List<Store> storeList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();

    @FXML
    private TableView<Item> itemTableView2;

    @FXML
    private TableColumn<Item, String> idTableColumn2;

    @FXML
    private TableColumn<Item, String> nameTableColumn2;

    @FXML
    private TableColumn<Item, String> categoryTableColumn2;

    @FXML
    private TableColumn<Item, String> widthTableColumn2;

    @FXML
    private TableColumn<Item, String> heightTableColumn2;

    @FXML
    private TableColumn<Item, String> lengthTableColumn2;

    @FXML
    private TableColumn<Item, String> productionTableColumn2;

    @FXML
    private TableColumn<Item, String> sellingTableColumn2;

    @FXML
    private ComboBox comboBox2;

    @FXML
    public void initialize(){
        storeList.clear();
        itemList.clear();

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            storeList = Database.getAllStoresFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        ObservableList<Store> ducaniObservableList = FXCollections.observableList(storeList);
        ObservableList<Store> options =
                FXCollections.observableList(storeList);
        comboBox2.setItems(options);

    }
    @FXML
    protected void onSearchButtonClick2() {
        if (!comboBox2.getSelectionModel().isEmpty()){
            Store ducan = (Store)comboBox2.getSelectionModel().getSelectedItem();
            try (Connection connection = Database.connectToDatabase()){
                System.out.println("Spojio sam se na bazu!");

                itemList = Database.getAllItemsFromCertainStore(connection, ducan.getId());

            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }
            ObservableList<Item> itemObservableList = FXCollections.observableList(itemList);
            itemTableView2.setItems(itemObservableList);
            /*idTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
            });*/
            nameTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getName());
            });
            /*categoryTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoryID()));
            });*/
            widthTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getWidth().toString());
            });
            heightTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getHeight().toString());
            });
            lengthTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getLength().toString());
            });
            productionTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getProductionCost().toString());
            });
            sellingTableColumn2.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getSellingPrice().toString());
            });
        }
    }
}
