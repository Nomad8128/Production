package hr.java.production;

import database.Database;
import hr.java.production.model.Category;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchCategoriesController {

    public static void openCategories(List<Category> categories) {

        String FILE_NAME = "dat/categories.txt";
        int brojacLinija=1;
        int BROJAC_KATEGORIJA = 0;
        long ajdi = 0;
        String ime = null;
        String opis = null;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 3 == 1){
                    ajdi = Long.parseLong(line);
                }
                if (brojacLinija % 3 == 2){
                    ime = line;
                }
                if (brojacLinija % 3 == 0){
                    opis = line;
                    categories.add(new Category(ajdi, ime, opis));
                    BROJAC_KATEGORIJA++;
                }
                brojacLinija++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

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
    private TextField firstTextField;

    @FXML
    private TableView<Category> categoryTableView;

    @FXML
    private TableColumn<Category, String> idTableColumn;

    @FXML
    private TableColumn<Category, String> nameTableColumn;

    @FXML
    private TableColumn<Category, String> descriptionTableColumn;

    @FXML
    public void initialize(){
        final Logger logger = LoggerFactory.getLogger(SearchCategoriesController.class);
        categoryList.clear();
        System.out.println("Initialize has been executed!");
        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            categoryList = Database.getAllCategoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        //brojac=categoryList.size()+1;

        //openCategories(categoryList);

        ObservableList<Category> categoryObservableList = FXCollections.observableList(categoryList);
        categoryTableView.setItems(categoryObservableList);

        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });

        /*idTableColumn.setCellValueFactory(cellData -> {
            Optional<Long> test = Optional.ofNullable(cellData.getValue().getId());
            if (test.isPresent()){
                return new SimpleStringProperty(cellData.getValue().getId().toString());
            }else {
                return new SimpleStringProperty("0");
            }
        });*/
        descriptionTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });

        //serijalizacija(categoryList, logger, "dat/kategorije.dat");
    }

    @FXML
    protected void onSearchButtonClick() {
        System.out.println("Search button clicked!");
        String enteredName = firstTextField.getText();
        List<Category> filteredList = categoryList.stream()
                .filter(s -> s.getName().toLowerCase().contains(enteredName.toLowerCase()))
                .collect(Collectors.toList());
        categoryTableView.setItems(FXCollections.observableList(filteredList));
    }
}
