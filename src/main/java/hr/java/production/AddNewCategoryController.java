package hr.java.production;

import database.Database;
import hr.java.production.model.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddNewCategoryController {


    private static List<Category> categoryList = new ArrayList<>();
    public static final String SERIALIZATION_FILE_NAME = "dat/kategorije.dat";
    public int brojac = categoryList.size()+1;

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
    public void initialize(){
        final Logger logger = LoggerFactory.getLogger(AddNewCategoryController.class);
        categoryList.clear();
        System.out.println("Initialize has been executed!");
        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            categoryList = Database.getAllCategoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        brojac=categoryList.size()+1;
        ID.setText(String.valueOf(brojac));

        //serijalizacija(categoryList, logger, "dat/kategorije.dat");
    }
    @FXML
    protected void onSaveButtonClick() {
        final Logger logger = LoggerFactory.getLogger(AddNewCategoryController.class);
        System.out.println("Search button clicked!");
        Optional<String> prvi = Optional.ofNullable(firstTextField.getText());
        Optional<String> drugi = Optional.ofNullable(secondTextField.getText());
        if (!firstTextField.getText().equals("") && !secondTextField.getText().equals("")){
            System.out.println("Uspjesno kreirana kategorija");
            categoryList.add(new Category(brojac, prvi.get(), drugi.get()));

            try (Connection connection = Database.connectToDatabase()) {
                Database.insertNewCategoryToDatabase(connection, new Category(brojac, prvi.get(), drugi.get()));
            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }

            brojac=categoryList.size()+1;
            ID.setText(String.valueOf(brojac));
            //serijalizacija(categoryList, logger, "dat/kategorije.dat");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.setContentText("Uspjesno kreirana kategorija!");
            a.show();

        }
        else if (firstTextField.getText().equals("") || secondTextField.getText().equals("")){
            System.out.println("Moraju sva polja biti puna");
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Moraju sva polja biti puna!");
            a.show();

        }
    }

}
