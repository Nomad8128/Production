package hr.java.production;

import database.Database;
import hr.java.production.model.Address;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchAddressController {
    private static List<Address> addressList = new ArrayList<>();

    @FXML
    private TableView<Address> addressTableView;

    @FXML
    private TableColumn<Address, String> idTableColumn;

    @FXML
    private TableColumn<Address, String> streetTableColumn;

    @FXML
    private TableColumn<Address, String> numberTableColumn;

    @FXML
    private TableColumn<Address, String> cityTableColumn;

    @FXML
    private TableColumn<Address, String> postalTableColumn;


    @FXML
    public void initialize(){
        addressList.clear();
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            addressList = Database.getAllAddressesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        ObservableList<Address> addressObservableList = FXCollections.observableList(addressList);

        addressTableView.setItems(addressObservableList);

        /*idTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
        });*/

        streetTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getStreet());
        });
        numberTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getHouseNumber()));
        });
        cityTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCity().getNazivGrada());
        });
        postalTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getCity().getPostanskiBroj());
        });
    }
    @FXML
    protected void onSearchButtonClick() {

    }
}
