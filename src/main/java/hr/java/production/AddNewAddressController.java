package hr.java.production;

import database.Database;
import hr.java.production.enums.Grad;
import hr.java.production.model.Address;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewAddressController {
    private static List<Address> addressList = new ArrayList<>();
    private static List<Grad> gradList = new ArrayList<>();

    @FXML
    private TextField firstTextField;

    @FXML
    private TextField secondTextField;

    @FXML
    private ComboBox comboBox;

    @FXML
    public void initialize(){
        addressList.clear();
        gradList.clear();
        for(Grad city : Grad.values()){
            gradList.add(city);
        }
        ObservableList<Grad> gradObservableList = FXCollections.observableList(gradList);
        ObservableList<Grad> options =
                FXCollections.observableList(gradList);
        comboBox.setItems(options);
    }
    @FXML
    protected void onSaveButtonClick() {
        if (!firstTextField.getText().equals("") && !secondTextField.getText().equals("") && !comboBox.getSelectionModel().isEmpty()){
            Grad grad = (Grad)comboBox.getSelectionModel().getSelectedItem();
            Address temp = new Address.Builder(firstTextField.getText()).atHouseNumber(secondTextField.getText()).inCity(grad).build();
            try (Connection connection = Database.connectToDatabase()) {
                Database.insertNewAddressToDatabase(connection, temp);

            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
                ex.printStackTrace();
            }
        }

    }
}


