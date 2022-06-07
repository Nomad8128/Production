package hr.java.production;

import database.Database;
import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchItemsController {
    public static void openCategories(List<Category> categories) {

        String FILE_NAME = "dat/categories.txt";
        int brojacLinija = 1;
        int BROJAC_KATEGORIJA = 0;
        long ajdi = 0;
        String ime = null;
        String opis = null;
        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 3 == 1) {
                    ajdi = Long.parseLong(line);
                }
                if (brojacLinija % 3 == 2) {
                    ime = line;
                }
                if (brojacLinija % 3 == 0) {
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

    public static void openArticles(List<Item> items, List<Category> categories) {
        String FILE_NAME = "dat/items.txt";
        int brojacLinija = 1;
        int BROJAC_AJTEMA = 0;

        long ajdi = 0;
        String name = null;
        String category = null;
        Category temp = new Category();
        BigDecimal width = new BigDecimal("0");
        BigDecimal height = new BigDecimal("0");
        BigDecimal length = new BigDecimal("0");
        BigDecimal productionCost = new BigDecimal("0");
        BigDecimal sellingPrice = new BigDecimal("0");
        BigDecimal popust = new BigDecimal("0");
        int odabir = 0;
        int special1 = 0;
        BigDecimal special2 = new BigDecimal("0");

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 12 == 1) {
                    ajdi = Long.parseLong(line);
                }
                if (brojacLinija % 12 == 2) {
                    name = line;
                }
                if (brojacLinija % 12 == 3) {
                    category = line;
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getName().equals(category)) {
                            temp = categories.get(i);
                        }
                    }
                }
                if (brojacLinija % 12 == 4) {
                    width = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 5) {
                    height = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 6) {
                    length = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 7) {
                    productionCost = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 8) {
                    sellingPrice = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 9) {
                    odabir = Integer.parseInt(line);
                }
                if (brojacLinija % 12 == 10) {
                    special1 = Integer.parseInt(line);
                }
                if (brojacLinija % 12 == 11) {
                    special2 = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 0) {
                    popust = new BigDecimal(line);
                    if (odabir == 0) {
                        Item ajtem = new Item(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust);
                        items.add(ajtem);
                    }
                    if (odabir == 1) {
                        Item ajtem = new Laptop(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust, special1);
                        items.add(ajtem);
                    }
                    if (odabir == 2) {
                        if (special1 == 1) {
                            Item ajtem = new Jabuka(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust, special2);
                            items.add(ajtem);
                        }
                        if (special1 == 2) {
                            Item ajtem = new Kruska(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust, special2);
                            items.add(ajtem);
                        }
                    }

                    BROJAC_AJTEMA++;
                }

                brojacLinija++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static final String SERIALIZATION_FILE_NAME1 = "dat/kategorije.dat";

    public static final String SERIALIZATION_FILE_NAME2 = "dat/artikli.dat";

    private static List<Category> categoryList = new ArrayList<>();

    private static List<Item> itemList = new ArrayList<>();

    public int brojac1 = categoryList.size()+1;

    public int brojac2 = itemList.size()+1;

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
    private TableView<Item> itemTableView;

    @FXML
    private ComboBox comboBox;

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
    public void initialize() {
        final Logger logger = LoggerFactory.getLogger(SearchCategoriesController.class);
        categoryList.clear();
        itemList.clear();

        System.out.println("Initialize has been executed!");

        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");
        //deserijalizacija(itemList, logger, "dat/artikli.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            categoryList = Database.getAllCategoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            itemList = Database.getAllItemsFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        brojac1 = categoryList.size()+1;
        brojac2 = itemList.size()+1;
        //openCategories(categoryList);
        //openArticles(itemList, categoryList);

        //categoryList.add(new Category(0, "", ""));
        ObservableList<Category> kategorijeObservableList = FXCollections.observableList(categoryList);
        ObservableList<Item> itemObservableList = FXCollections.observableList(itemList);

        itemTableView.setItems(itemObservableList);

        /*idTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getId()));
        });*/

        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        categoryTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(cellData.getValue().getCategory().getName()));
        });
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
        ObservableList<Category> options =
                FXCollections.observableList(kategorijeObservableList);
        comboBox.setItems(options);


        //serijalizacija(categoryList, logger, "dat/kategorije.dat");
        //serijalizacija(itemList, logger, "dat/artikli.dat");
    }

    @FXML
    protected void onSearchButtonClick() {
        System.out.println("Search button clicked!");
        String enteredName = firstTextField.getText();
        Optional test = Optional.ofNullable(comboBox.getValue());

        if (test.isPresent()){
            List<Item> filteredList = itemList.stream()
                    .filter(s -> s.getName().toLowerCase().contains(enteredName.toLowerCase()) && s.getCategory().getName().contains(comboBox.getValue().toString()))
                    .collect(Collectors.toList());
            itemTableView.setItems(FXCollections.observableList(filteredList));
        }else {
            List<Item> filteredList = itemList.stream()
                    .filter(s -> s.getName().toLowerCase().contains(enteredName.toLowerCase()))
                    .collect(Collectors.toList());
            itemTableView.setItems(FXCollections.observableList(filteredList));
        }


    }
    @FXML
    protected void onClearButtonClick() {
        comboBox.getSelectionModel().clearSelection();
    }

}

