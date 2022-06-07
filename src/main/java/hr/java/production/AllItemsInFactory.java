package hr.java.production;

import database.Database;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
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

public class AllItemsInFactory {

    private static List<Factory> factoryList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();

    @FXML
    private TableView<Item> itemTableView;

    @FXML
    private TableColumn<Item, String> idTableColumn;

    @FXML
    private TableColumn<Item, String> nameTableColumn;

    @FXML
    private TableColumn<Item, String> categoryTableColumn;

    @FXML
    private TableColumn<Item, String> widthTableColumn;

    @FXML
    private TableColumn<Item, String> heightTableColumn;

    @FXML
    private TableColumn<Item, String> lengthTableColumn;

    @FXML
    private TableColumn<Item, String> productionTableColumn;

    @FXML
    private TableColumn<Item, String> sellingTableColumn;

    @FXML
    private ComboBox comboBox1;

    @FXML
    public void initialize(){
        factoryList.clear();
        itemList.clear();

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            factoryList = Database.getAllFactoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        ObservableList<Factory> tvorniceObservableList = FXCollections.observableList(factoryList);
        ObservableList<Factory> options =
                FXCollections.observableList(factoryList);
        comboBox1.setItems(options);

    }
    @FXML
    protected void onSearchButtonClick() {
        if (!comboBox1.getSelectionModel().isEmpty()){
            Factory tvornica = (Factory)comboBox1.getSelectionModel().getSelectedItem();
            try (Connection connection = Database.connectToDatabase()){
                System.out.println("Spojio sam se na bazu!");

                itemList = Database.getAllItemsFromCertainFactory(connection, tvornica.getId());

            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }
            ObservableList<Item> itemObservableList = FXCollections.observableList(itemList);
            itemTableView.setItems(itemObservableList);
            /*idTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
            });*/
            nameTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getName());
            });
            /*categoryTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(String.valueOf(cellData.getValue().getCategoryID()));
            });*/
            widthTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getWidth().toString());
            });
            heightTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getHeight().toString());
            });
            lengthTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getLength().toString());
            });
            productionTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getProductionCost().toString());
            });
            sellingTableColumn.setCellValueFactory(cellData -> {
                return new SimpleStringProperty(cellData.getValue().getSellingPrice().toString());
            });
        }
    }
}
