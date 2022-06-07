package hr.java.production;

import database.Database;
import hr.java.production.enums.Grad;
import hr.java.production.model.*;
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

public class AddNewStoreController {
    private static List<Store> storeList = new ArrayList<>();


    public int brojac = storeList.size()+1;

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
    private TextField secondTextField;

    @FXML
    public void initialize() {
        final Logger logger = LoggerFactory.getLogger(AddNewStoreController.class);

        storeList.clear();
        //deserijalizacija(storeList, logger, "dat/ducani.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            storeList = Database.getAllStoresFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        brojac = storeList.size()+1;
        ID.setText(String.valueOf(brojac));

        //serijalizacija(storeList, logger, "dat/ducani.dat");
    }
    @FXML
    protected void onSaveButtonClick() {
        final Logger logger = LoggerFactory.getLogger(AddNewStoreController.class);
        Alert a = new Alert(Alert.AlertType.NONE);

        if (!firstTextField.getText().equals("") && !secondTextField.getText().equals("")){
            String name = firstTextField.getText();
            String webAddress = secondTextField.getText();
            storeList.add(new Store((long) brojac, name, webAddress));

            brojac = storeList.size()+1;
            ID.setText(String.valueOf(brojac));

            try (Connection connection = Database.connectToDatabase()) {
                Database.insertNewStoreToDatabase(connection, new Store((long) brojac, name, webAddress));

            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
                ex.printStackTrace();
            }

            //serijalizacija(storeList, logger, "dat/ducani.dat");
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Uspjesno kreiran ducan!");
            a.show();
        }else if (firstTextField.getText().equals("") || secondTextField.getText().equals("")){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Moraju sva polja biti puna!");
            a.show();
        }

    }
}
