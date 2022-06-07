package hr.java.production;

import database.Database;
import hr.java.production.model.Category;
import hr.java.production.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AddNewItemController {

    private static List<Category> categoryList = new ArrayList<>();
    private static List<Item> itemList = new ArrayList<>();

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
    private TextField firstTextField;

    @FXML
    private ComboBox comboBox;

    @FXML
    private TextField secondTextField;

    @FXML
    private TextField thirdTextField;

    @FXML
    private TextField fourthTextField;

    @FXML
    private TextField fifthTextField;

    @FXML
    private TextField sixthTextField;

    @FXML
    public void initialize(){
        final Logger logger = LoggerFactory.getLogger(AddNewItemController.class);
        categoryList.clear();
        itemList.clear();

        System.out.println("Initialize has been executed!");

        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            categoryList = Database.getAllCategoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        //deserijalizacija(itemList, logger, "dat/artikli.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            itemList = Database.getAllItemsFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        ObservableList<Category> kategorijeObservableList = FXCollections.observableList(categoryList);
        ObservableList<Category> options =
                FXCollections.observableList(kategorijeObservableList);
        comboBox.setItems(options);



        //serijalizacija(categoryList, logger, "dat/kategorije.dat");
        //serijalizacija(itemList, logger, "dat/artikli.dat");
    }

    @FXML
    protected void onSaveButtonClick() {
        Alert a = new Alert(Alert.AlertType.NONE);

        boolean isMyComboBoxEmpty = comboBox.getSelectionModel().isEmpty();

        final Logger logger = LoggerFactory.getLogger(AddNewItemController.class);
        System.out.println("Search button clicked!");

        if (!firstTextField.getText().equals("") && !secondTextField.getText().equals("") && !thirdTextField.getText().equals("") && !fourthTextField.getText().equals("") && !fifthTextField.getText().equals("") && !sixthTextField.getText().equals("") && !isMyComboBoxEmpty){
            String msg1 = "", msg2 = "", msg3 = "", msg4 = "", msg5 = "";
            BigDecimal width = new BigDecimal(0);
            BigDecimal height = new BigDecimal(0);
            BigDecimal length = new BigDecimal(0);
            BigDecimal production_cost = new BigDecimal(0);
            BigDecimal selling_price = new BigDecimal(0);
            boolean flag = false;
            String name = firstTextField.getText();
            Category kategorija = (Category)comboBox.getSelectionModel().getSelectedItem();
            try {
                width = new BigDecimal(secondTextField.getText());
            } catch (NumberFormatException e){
                flag = true;
                msg1="Width must be in decimal format\n";
            }
            try{
                height = new BigDecimal(thirdTextField.getText());
            } catch (NumberFormatException e){
                flag = true;
                msg2="Height must be in decimal format\n";
            }
            try{
                length = new BigDecimal(fourthTextField.getText());
            } catch (NumberFormatException e){
                flag = true;
                msg3="Length must be in decimal format\n";
            }
            try{
                production_cost = new BigDecimal(fifthTextField.getText());
            } catch (NumberFormatException e){
                flag = true;
                msg4="Production cost must be in decimal format\n";
            }
            try {
                selling_price = new BigDecimal(sixthTextField.getText());
            } catch (NumberFormatException e) {
                flag = true;
                msg5="Selling price must be in decimal format\n";
            }
            if (flag){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText(msg1+msg2+msg3+msg4+msg5);
                a.show();
            } else {
                Item temp = new Item(name, kategorija, width, length, height, production_cost, selling_price);
                itemList.add(temp);

                try (Connection connection = Database.connectToDatabase()) {
                    Database.insertNewArticleToDatabase(connection, new Item(name, kategorija, width, length, height, production_cost, selling_price));
                } catch (SQLException | IOException ex) {
                    System.out.println("Pogreska kod spajanja na bazu!");
                    ex.printStackTrace();
                }

                //serijalizacija(itemList, logger, "dat/artikli.dat");
                a.setAlertType(Alert.AlertType.INFORMATION);
                a.setContentText("Uspjesno kreiran artikl!");
                a.show();
            }

        }else if (firstTextField.getText().equals("") || secondTextField.getText().equals("") || thirdTextField.getText().equals("") || fourthTextField.getText().equals("") || fifthTextField.getText().equals("") || sixthTextField.getText().equals("") || isMyComboBoxEmpty) {
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Moraju sva polja biti puna!");
            a.show();
        }
    }
}
