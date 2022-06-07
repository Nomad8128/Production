package hr.java.production;

import database.Database;
import hr.java.production.enums.Grad;
import hr.java.production.model.Address;
import hr.java.production.model.Category;
import hr.java.production.model.Factory;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddNewFactoryController {

    private static List<Address> addressList = new ArrayList<>();
    private static List<Factory> factoryList = new ArrayList<>();
    private static final List<Category> categoryList = new ArrayList<>();
    private static final List<Item> itemList = new ArrayList<>();
    private static final List<Grad> cityList = new ArrayList<>();

    public int brojac = factoryList.size()+1;

    public static <T> void deserijalizacija(List<T> list, Logger logger, String lokacija){
        int counter = 0;
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(lokacija))) {
            while (true) {
                try {
                    T temp = (T) in.readObject();
                    counter++;
                    list.add(temp);
                    logger.info("Deserijaliziran objekt!");
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
            ex.printStackTrace();
            logger.error("Došlo je do pogreške u čitanju datoteke!", ex);
        }
        if (counter!=0){
            System.out.print("Postoji " + counter + " prijasnjih objekata!\n");
        }
    }
    public static <T> void serijalizacija(List<T> list, Logger logger, String lokacija){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(lokacija))) {
            for (int i = 0; i < list.size(); i++){
                out.writeObject(list.get(i));
                logger.info("Serijaliziran objekt!");
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    private Label ID;

    @FXML
    private TextField firstTextField;

    @FXML
    private ComboBox comboBox;

    @FXML
    public void initialize() {
        final Logger logger = LoggerFactory.getLogger(AddNewCategoryController.class);

        addressList.clear();
        categoryList.clear();
        itemList.clear();
        factoryList.clear();
        cityList.clear();

        //deserijalizacija(addressList, logger, "dat/adrese.dat");
        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");
        deserijalizacija(itemList, logger, "dat/artikli.dat");
        //deserijalizacija(factoryList, logger, "dat/tvornice.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            addressList = Database.getAllAddressesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            factoryList = Database.getAllFactoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        brojac = factoryList.size()+1;
        ID.setText(String.valueOf(brojac));

        ObservableList<Address> adreseObservableList = FXCollections.observableList(addressList);
        ObservableList<Address> options =
                FXCollections.observableList(addressList);
        comboBox.setItems(options);

        //serijalizacija(addressList, logger, "dat/adrese.dat");
        serijalizacija(categoryList, logger, "dat/kategorije.dat");
        serijalizacija(itemList, logger, "dat/artikli.dat");
        //serijalizacija(factoryList, logger, "dat/tvornice.dat");
    }

    @FXML
    protected void onSaveButtonClick() {
        final Logger logger = LoggerFactory.getLogger(AddNewFactoryController.class);

        Alert a = new Alert(Alert.AlertType.NONE);
        boolean isMyComboBoxEmpty = comboBox.getSelectionModel().isEmpty();

        if (!firstTextField.getText().equals("") && !isMyComboBoxEmpty){
            String name = firstTextField.getText();

            //Address novaAdresa = new Address.Builder(street).atHouseNumber(houseNumber).inCity(city).build();
            Address novaAdresa = (Address)comboBox.getSelectionModel().getSelectedItem();
            addressList.add(novaAdresa);
            factoryList.add(new Factory(brojac, name, novaAdresa));
            Factory temp = new Factory(brojac, name, novaAdresa);

            brojac=factoryList.size()+1;
            ID.setText(String.valueOf(brojac));

            try (Connection connection = Database.connectToDatabase()) {
                Database.insertNewFactoryToDatabase(connection, new Factory(brojac, name, novaAdresa));
                /*for (int i = 0; i < addressList.size(); i++){
                    System.out.println(addressList.get(i).getId());
                    System.out.println(addressList.get(i).getCity());
                    System.out.println(addressList.get(i).getCity().getNazivGrada());
                    System.out.println(addressList.get(i).getCity().getPostanskiBroj());
                    System.out.println(addressList.get(i).getStreet());
                    System.out.println(addressList.get(i).getHouseNumber());
                    System.out.println("Factory get adres get id:" + temp.getAddress().getId());
                    System.out.println("Factory get adres id:" + temp.getAddressId());
                }*/
            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
                ex.printStackTrace();
            }
            serijalizacija(addressList, logger, "dat/adrese.dat");
            //serijalizacija(factoryList, logger, "dat/tvornice.dat");

            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Uspjesno kreirana tvornica!");
            a.show();

        }else if(firstTextField.getText().equals("") || isMyComboBoxEmpty){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Moraju sva polja biti puna!");
            a.show();
        }


    }
}
