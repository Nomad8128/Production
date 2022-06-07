package hr.java.production;

import database.Database;
import hr.java.production.enums.Grad;
import hr.java.production.model.*;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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

public class SearchFactoriesController {
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

    public static void openAddresses(List<Address> addresses) {
        String FILE_NAME = "dat/addresses.txt";
        int brojacLinija=1;
        int BROJAC_ADRESA = 0;

        String ulica = null;
        String kucniBroj = null;
        String gradNaziv = null;
        Grad city = Grad.Default;

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 3 == 1){
                    ulica = line;
                }
                if (brojacLinija % 3 == 2){
                    kucniBroj = line;
                }
                if (brojacLinija % 3 == 0){
                    gradNaziv = line;
                    for (Grad citi : Grad.values()) {
                        if(gradNaziv.equals(citi.getNazivGrada())){
                            city = citi;
                        }
                    }
                    addresses.add(new Address.Builder(ulica).atHouseNumber(kucniBroj).inCity(city).build());
                    BROJAC_ADRESA++;
                }
                brojacLinija++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    public static void openFactories(List<Factory> factories, List<Address> addresses, List<Item> items) {
        String FILE_NAME = "dat/factories.txt";
        int brojacLinija=1;
        int BROJAC_TVORNICA = 0;

        long ajdi = 0;
        String ime = null;
        int adresa_identifikator=0;
        Address example = new Address.Builder("Default").atHouseNumber("Default").inCity(Grad.Default).build();
        String artikli = null;

        try (BufferedReader in = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (brojacLinija % 4 == 1){
                    ajdi = Long.parseLong(line);
                }
                if (brojacLinija % 4 == 2){
                    ime = line;
                }
                if (brojacLinija % 4 == 3){
                    adresa_identifikator=Integer.parseInt(line);
                    example = addresses.get(adresa_identifikator-1);
                }
                if (brojacLinija % 4 == 0){
                    artikli = line;
                    String[] odabiri = artikli.split(",");

                    Set<Item> artikli_za_tvornicu = new HashSet<Item>();
                    for (int i = 0; i < odabiri.length;i++){
                        //System.out.println("DANGER ZONE");
                        //System.out.println(odabiri[i]);
                        for (int j = 0; j < items.size(); j++){
                            if (Integer.parseInt(odabiri[i])==items.get(j).getId()){
                                artikli_za_tvornicu.add(items.get(j));
                            }
                        }
                        //artikli_za_tvornicu.add(items.get(Integer.parseInt(odabiri[i])-1));
                    }
                    factories.add(new Factory(ajdi, ime, example, artikli_za_tvornicu));
                    BROJAC_TVORNICA++;
                }
                brojacLinija++;
            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static List<Factory> factoryList = new ArrayList<>();

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
    private TableView<Factory> factoryTableView;

    @FXML
    private TableColumn<Factory, String> idTableColumn;

    @FXML
    private TableColumn<Factory, String> nameTableColumn;

    @FXML
    private TableColumn<Factory, String> ulicaTableColumn;

    @FXML
    private TableColumn<Factory, String> brojTableColumn;

    @FXML
    private TableColumn<Factory, String> gradTableColumn;

    @FXML
    private TableColumn<Factory, String> itemsTableColumn;

    @FXML
    public void initialize() {
        final Logger logger = LoggerFactory.getLogger(SearchFactoriesController.class);

        factoryList.clear();

        //deserijalizacija(factoryList, logger, "dat/tvornice.dat");

        try (Connection connection = Database.connectToDatabase()){
            System.out.println("Spojio sam se na bazu!");

            factoryList = Database.getAllFactoriesFromDatabase(connection);

        } catch (SQLException | IOException ex) {
            System.out.println("Pogreska kod spajanja na bazu!");
        }

        //openAddresses(addressList);
        //openCategories(categoryList);
        //openArticles(itemList, categoryList);
        //openFactories(factoryList, addressList, itemList);

        ObservableList<Factory> factoryObservableList = FXCollections.observableList(factoryList);

        factoryTableView.setItems(factoryObservableList);

        /*idTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getId().toString());
        });*/
        nameTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getName());
        });
        ulicaTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getAddress().getStreet());
        });
        brojTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getAddress().getHouseNumber());
        });
        gradTableColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getAddress().getCity().getNazivGrada());
            //return new SimpleStringProperty(cellData.getValue().getAddress().getHouseNumber());
        });
        itemsTableColumn.setCellValueFactory(cellData -> {
            List<Item> itemList = new ArrayList<>();

            try (Connection connection = Database.connectToDatabase()){
                System.out.println("Spojio sam se na bazu!");

                itemList = Database.getAllItemsFromCertainFactory(connection, cellData.getValue().getId());

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

        //serijalizacija(factoryList, logger, "dat/tvornice.dat");
    }
    @FXML
    protected void onSearchButtonClick() {
        System.out.println("Search button clicked!");
        String enteredName = firstTextField.getText();
        List<Factory> filteredList = factoryList.stream()
                .filter(s -> s.getName().toLowerCase().contains(enteredName.toLowerCase()))
                .collect(Collectors.toList());
        factoryTableView.setItems(FXCollections.observableList(filteredList));
    }
}
