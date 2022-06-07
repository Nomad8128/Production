package hr.java.production;

import database.Database;
import hr.java.production.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchStoresController {
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

    public static void openArticles(List<Item> items, List<Category> categories) {
        String FILE_NAME = "dat/items.txt";
        int brojacLinija=1;
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
        int special1= 0;
        BigDecimal special2 = new BigDecimal("0");

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 12 == 1){
                    ajdi = Long.parseLong(line);
                }
                if (brojacLinija % 12 == 2){
                    name = line;
                }
                if (brojacLinija % 12 == 3){
                    category = line;
                    for (int i = 0; i < categories.size(); i++){
                        if (categories.get(i).getName().equals(category)){
                            temp = categories.get(i);
                        }
                    }
                }
                if (brojacLinija % 12 == 4){
                    width = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 5){
                    height = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 6){
                    length = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 7){
                    productionCost = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 8){
                    sellingPrice = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 9){
                    odabir = Integer.parseInt(line);
                }
                if (brojacLinija % 12 == 10){
                    special1 = Integer.parseInt(line);
                }
                if (brojacLinija % 12 == 11){
                    special2 = new BigDecimal(line);
                }
                if (brojacLinija % 12 == 0){
                    popust = new BigDecimal(line);
                    if (odabir==0){
                        Item ajtem = new Item(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust);
                        items.add(ajtem);
                    }
                    if (odabir==1){
                        Item ajtem = new Laptop(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust, special1);
                        items.add(ajtem);
                    }
                    if (odabir==2){
                        if (special1 == 1){
                            Item ajtem = new Jabuka(ajdi, name, temp, width, height, length, productionCost, sellingPrice, popust, special2);
                            items.add(ajtem);
                        }
                        if (special1 == 2){
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

    public static void openStores(List<Store> stores, List<Item> items){
        String FILE_NAME = "dat/stores.txt";
        int brojacLinija=1;
        int BROJAC_DUCANA = 0;

        long ajdi = 0;
        String ime = null;
        String artikli = null;

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
                    artikli = line;
                    String[] odabiri = artikli.split(",");
                    Set<Item> artikli_za_ducan = new HashSet<Item>();
                    for (int i = 0; i < odabiri.length;i++){
                        for (int j = 0; j < items.size(); j++){
                            if (Integer.parseInt(odabiri[i])==items.get(j).getId()){
                                artikli_za_ducan.add(items.get(j));
                            }
                        }

                    }
                    stores.add(new Store(ajdi, ime, artikli_za_ducan));
                    BROJAC_DUCANA++;
                }

                brojacLinija++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private static final List<Category> categoryList = new ArrayList<>();

    private static final List<Item> itemList = new ArrayList<>();

    private static List<Store> storeList = new ArrayList<>();

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
    private TableView<Store> storeTableView;

    @FXML
    private TableColumn<Store, String> idTableColumn;

    @FXML
    private TableColumn<Store, String> nameTableColumn;

    @FXML
    private TableColumn<Store, String> adresaTableColumn;

    @FXML
    private TableColumn<Store, String> itemsTableColumn;

    @FXML
    public void initialize() {
        final Logger logger = LoggerFactory.getLogger(SearchStoresController.class);

        itemList.clear();
        categoryList.clear();
        storeList.clear();


        //openCategories(categoryList);
        //openArticles(itemList, categoryList);
        //openStores(storeList, itemList);
        //deserijalizacija(categoryList, logger, "dat/kategorije.dat");
        //deserijalizacija(itemList, logger, "dat/artikli.dat");
        //deserijalizacija(storeList, logger, "dat/ducani.dat");
        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            storeList = Database.getAllStoresFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        ObservableList<Store> storeObservableList = FXCollections.observableList(storeList);

        storeTableView.setItems(storeObservableList);

        /*idTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getId().toString());
        });*/

        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });

        adresaTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getWebAddress());
        });
        itemsTableColumn.setCellValueFactory(cellData -> {
            List<Item> itemList = new ArrayList<>();

            try (Connection connection = Database.connectToDatabase()){
                System.out.println("Spojio sam se na bazu!");

                itemList = Database.getAllItemsFromCertainStore(connection, cellData.getValue().getId());

            } catch (SQLException | IOException ex) {
                System.out.println("Pogreska kod spajanja na bazu!");
            }
            String test = "";
            for (int i = 0; i < itemList.size(); i++) {
                if (i < itemList.size()-1){
                    test+=itemList.get(i).getName() + ", ";
                }else {
                    test+=itemList.get(i).getName();
                }
            }
            return new SimpleStringProperty(test);
            //return new SimpleStringProperty(cellData.getValue().getAddress().getHouseNumber());
        });

        //serijalizacija(categoryList, logger, "dat/kategorije.dat");
        //serijalizacija(itemList, logger, "dat/artikli.dat");
        //serijalizacija(storeList, logger, "dat/ducani.dat");
    }
    @FXML
    protected void onSearchButtonClick() {
        System.out.println("Search button clicked!");
        String enteredName = firstTextField.getText();
        List<Store> filteredList = storeList.stream()
                .filter(s -> s.getName().toLowerCase().contains(enteredName.toLowerCase()))
                .collect(Collectors.toList());
        storeTableView.setItems(FXCollections.observableList(filteredList));
    }

}
