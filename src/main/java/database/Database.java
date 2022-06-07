package database;
import hr.java.production.enums.Grad;
import hr.java.production.model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    public static Connection connectToDatabase() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("src\\main\\resources\\database.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

        Connection veza = DriverManager.getConnection(databaseURL,
                databaseUsername, databasePassword);
        return veza;
    }
    public static List<Category> getAllCategoriesFromDatabase(Connection veza) throws SQLException{
        List<Category> categoryList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();

        ResultSet categoryResultSet = sqlStatement.executeQuery("SELECT * FROM category");

        while(categoryResultSet.next()){
            Long categoryId = categoryResultSet.getLong("ID");
            String categoryName = categoryResultSet.getString("NAME");
            String categoryDescription = categoryResultSet.getString("DESCRIPTION");

            Category newCategory = new Category(categoryId, categoryName, categoryDescription);
            categoryList.add(newCategory);
        }
        for (int i = 0; i < categoryList.size();i++){
            System.out.println(categoryList.get(i).getId());
            System.out.println(categoryList.get(i).getName());
            System.out.println(categoryList.get(i).getDescription());
        }
        return categoryList;
    }
    public static List<Item> getAllItemsFromDatabase(Connection veza) throws SQLException{
        List<Category> categoryList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();
        ResultSet categoryResultSet2 = sqlStatement.executeQuery("SELECT * FROM category");

        while(categoryResultSet2.next()){
            Long categoryId = categoryResultSet2.getLong("ID");
            String categoryName = categoryResultSet2.getString("NAME");
            String categoryDescription = categoryResultSet2.getString("DESCRIPTION");

            Category newCategory = new Category(categoryId, categoryName, categoryDescription);
            categoryList.add(newCategory);
        }
        ResultSet categoryResultSet = sqlStatement.executeQuery("SELECT * FROM item");

        while(categoryResultSet.next()){
            Long itemId = categoryResultSet.getLong("ID");
            Long categoryId = categoryResultSet.getLong("CATEGORY_ID");
            String itemName = categoryResultSet.getString("NAME");
            BigDecimal itemWidth = categoryResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = categoryResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = categoryResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = categoryResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = categoryResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++){
                if (categoryList.get(i).getId()==categoryId){
                    temp = categoryList.get(i);
                }
            }

            Item newItem = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
            itemList.add(newItem);
        }
        return itemList;
    }
    public static List<Address> getAllAddressesFromDatabase(Connection veza) throws SQLException{
        List<Address> addressList = new ArrayList<>();
        Statement sqlStatement = veza.createStatement();

        ResultSet addressResultSet = sqlStatement.executeQuery("SELECT * FROM address");

        while(addressResultSet.next()){
            Long addressId = addressResultSet.getLong("ID");
            String addressStreet = addressResultSet.getString("STREET");
            String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
            String cityName = addressResultSet.getString("CITY");
            int postalCode = addressResultSet.getInt("POSTAL_CODE");


            Grad temp = null;
            for (Grad city : Grad.values()){
                if (city.getNazivGrada().equals(cityName)){
                    temp = city;
                }
            }
            Address newAddress = new Address.Builder(addressStreet).atHouseNumber(houseNumber).inCity(temp).hasId(addressId).build();

            addressList.add(newAddress);
        }
        return addressList;
    }
    public static List<Factory> getAllFactoriesFromDatabase(Connection veza) throws SQLException{
        List<Address> addressList = getAllAddressesFromDatabase(veza);

        List<Factory> factoriesList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();

        ResultSet factoryResultSet = sqlStatement.executeQuery("SELECT * FROM factory");

        while(factoryResultSet.next()){
            Long factoryId = factoryResultSet.getLong("ID");
            String factoryName = factoryResultSet.getString("NAME");
            int addressId = factoryResultSet.getInt("ADDRESS_ID");

            Address temp = null;
            for(int i = 0; i < addressList.size(); i++){
                if (addressList.get(i).getId()==addressId){
                    temp = addressList.get(i);
                }
            }

            Factory newFactory = new Factory(factoryId, factoryName, temp, addressId);
            factoriesList.add(newFactory);
        }
        return factoriesList;
    }
    public static List<Store> getAllStoresFromDatabase(Connection veza) throws SQLException{
        List<Store> storeList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();

        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM store");

        while(storeResultSet.next()){
            Long storeId = storeResultSet.getLong("ID");
            String storeName = storeResultSet.getString("NAME");
            String storeAddress = storeResultSet.getString("WEB_ADDRESS");

            Store newStore = new Store(storeId, storeName, storeAddress);
            storeList.add(newStore);
        }
        return storeList;
    }
    public static List<Item> getAllItemsFromCertainFactory(Connection veza, long broj) throws  SQLException{
        List<Category> categoryList = getAllCategoriesFromDatabase(veza);;

        List<Item> itemList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY_ITEM FI, ITEM I WHERE FI.FACTORY_ID = " + broj + " AND FI.ITEM_ID = I.ID;");

        while(itemResultSet.next()) {
            Long itemId = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            String itemName = itemResultSet.getString("NAME");
            BigDecimal itemWidth = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    temp = categoryList.get(i);
                }
            }

            Item newItem = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
            itemList.add(newItem);
        }
        return itemList;
    }
    public static List<Item> getAllItemsFromCertainStore(Connection veza, long broj) throws SQLException{
        List<Category> categoryList = getAllCategoriesFromDatabase(veza);

        List<Item> itemList = new ArrayList<>();

        Statement sqlStatement = veza.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM STORE_ITEM SI, ITEM I WHERE SI.STORE_ID = " + broj + " AND SI.ITEM_ID = I.ID;");

        while(itemResultSet.next()) {
            Long itemId = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            String itemName = itemResultSet.getString("NAME");
            BigDecimal itemWidth = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    temp = categoryList.get(i);
                }
            }

            Item newItem = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
            itemList.add(newItem);
        }
        return itemList;
    }
    public static Item getItemfromItems(Connection veza, int broj) throws SQLException{
        List<Category> categoryList = getAllCategoriesFromDatabase(veza);

        Item item = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM ITEM WHERE ID = " + broj + ";");

        while(itemResultSet.next()) {
            Long itemId = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            String itemName = itemResultSet.getString("NAME");
            BigDecimal itemWidth = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    temp = categoryList.get(i);
                }
            }

            item = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
        }
        return item;
    }
    public static Item getItemfromFactories(Connection veza, int broj) throws SQLException{
        List<Category> categoryList = getAllCategoriesFromDatabase(veza);

        Item item = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY_ITEM WHERE ID = " + broj + ";");

        while(itemResultSet.next()) {
            Long itemId = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            String itemName = itemResultSet.getString("NAME");
            BigDecimal itemWidth = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    temp = categoryList.get(i);
                }
            }

            item = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
            break;
        }
        return item;
    }
    public static Item getItemfromStores(Connection veza, int broj) throws SQLException{
        List<Category> categoryList = getAllCategoriesFromDatabase(veza);

        Item item = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet itemResultSet = sqlStatement.executeQuery("SELECT * FROM STORE_ITEM WHERE ID = " + broj + ";");

        while(itemResultSet.next()) {
            Long itemId = itemResultSet.getLong("ID");
            Long categoryId = itemResultSet.getLong("CATEGORY_ID");
            String itemName = itemResultSet.getString("NAME");
            BigDecimal itemWidth = itemResultSet.getBigDecimal("WIDTH");
            BigDecimal itemHeight = itemResultSet.getBigDecimal("HEIGHT");
            BigDecimal itemLength = itemResultSet.getBigDecimal("LENGTH");
            BigDecimal productionCost = itemResultSet.getBigDecimal("PRODUCTION_COST");
            BigDecimal sellingPrice = itemResultSet.getBigDecimal("SELLING_PRICE");

            Category temp = null;
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() == categoryId) {
                    temp = categoryList.get(i);
                }
            }

            item = new Item(itemId, categoryId, itemName, itemWidth, itemHeight, itemLength, productionCost, sellingPrice, temp);
            break;
        }
        return item;
    }
    public static Address getAddressFromAddresses(Connection veza, int broj) throws SQLException{
        Address adresa = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet addressResultSet = sqlStatement.executeQuery("SELECT * FROM ADDRESS WHERE ID = " + broj + ";");

        while(addressResultSet.next()){
            Long addressId = addressResultSet.getLong("ID");
            String addressStreet = addressResultSet.getString("STREET");
            String houseNumber = addressResultSet.getString("HOUSE_NUMBER");
            String cityName = addressResultSet.getString("CITY");
            int postalCode = addressResultSet.getInt("POSTAL_CODE");


            Grad temp = null;
            for (Grad city : Grad.values()){
                if (city.getNazivGrada().equals(cityName)){
                    temp = city;
                }
            }
            adresa = new Address.Builder(addressStreet).atHouseNumber(houseNumber).inCity(temp).hasId(addressId).build();
            break;
        }
        return adresa;
    }
    public static Factory getFactoryFromFactories(Connection veza, int broj) throws SQLException{
        List<Address> addressList = getAllAddressesFromDatabase(veza);

        Factory factory = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet factoryResultSet = sqlStatement.executeQuery("SELECT * FROM FACTORY WHERE ID = " + broj + ";");

        while(factoryResultSet.next()){
            long factoryId = factoryResultSet.getLong("ID");
            String factoryName = factoryResultSet.getString("NAME");
            int addressId = factoryResultSet.getInt("ADDRESS_ID");

            Address temp = null;
            for(int i = 0; i < addressList.size(); i++){
                if (addressList.get(i).getId()==addressId){
                    temp = addressList.get(i);
                }
            }

            factory = new Factory(factoryId, factoryName, temp, addressId);
            break;
        }
        return factory;
    }
    public static Store getStoreFromStores(Connection veza, int broj) throws SQLException{
        Store store = null;

        Statement sqlStatement = veza.createStatement();

        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM STORE WHERE ID = " + broj + ";");

        while(storeResultSet.next()){
            Long storeId = storeResultSet.getLong("ID");
            String storeName = storeResultSet.getString("NAME");
            String storeAddress = storeResultSet.getString("WEB_ADDRESS");

            store = new Store(storeId, storeName, storeAddress);
            break;
        }
        return store;
    }
    public static void insertNewCategoryToDatabase(Connection veza, Category category) throws SQLException, IOException{
        PreparedStatement stmt = veza.prepareStatement("INSERT INTO CATEGORY (NAME, DESCRIPTION) VALUES(?, ?)");

        stmt.setString(1, category.getName());
        stmt.setString(2, category.getDescription());

        stmt.executeUpdate();
    }
    public static void insertNewArticleToDatabase(Connection veza, Item item) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO ITEM (CATEGORY_ID, NAME, WIDTH, HEIGHT, LENGTH, PRODUCTION_COST, SELLING_PRICE) VALUES(?, ?, ?, ?, ?, ?, ?)");

        stmt.setLong(1, item.getCategory().getId());
        stmt.setString(2, item.getName());
        stmt.setBigDecimal(3, item.getWidth());
        stmt.setBigDecimal(4, item.getHeight());
        stmt.setBigDecimal(5, item.getLength());
        stmt.setBigDecimal(6, item.getProductionCost());
        stmt.setBigDecimal(7, item.getSellingPrice());

        stmt.executeUpdate();
        connection.close();
    }
    public static void insertNewAddressToDatabase(Connection veza, Address adresa) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO ADDRESS (STREET, HOUSE_NUMBER, CITY, POSTAL_CODE) VALUES(?, ?, ?, ?)");

        stmt.setString(1, adresa.getStreet());
        stmt.setString(2, adresa.getHouseNumber());
        stmt.setString(3, adresa.getCity().getNazivGrada());
        stmt.setString(4, adresa.getCity().getPostanskiBroj());

        stmt.executeUpdate();
        connection.close();
    }
    public static void insertNewFactoryToDatabase(Connection veza, Factory tvornica) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO FACTORY (NAME, ADDRESS_ID) VALUES(?, ?)");

        stmt.setString(1, tvornica.getName());
        stmt.setLong(2, tvornica.getAddress().getId());


        stmt.executeUpdate();
        connection.close();
    }
    public static void insertNewStoreToDatabase(Connection veza, Store ducan) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO STORE (NAME, WEB_ADDRESS) VALUES(?, ?)");

        stmt.setString(1, ducan.getName());
        stmt.setString(2, ducan.getWebAddress());


        stmt.executeUpdate();
        connection.close();
    }
    public static void insertItemIntoFactory(Connection veza, long broj1, long broj2) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO FACTORY_ITEM (FACTORY_ID, ITEM_ID) VALUES(?, ?)");
        BigDecimal factoryID=new BigDecimal(broj1);
        BigDecimal itemID=new BigDecimal(broj2);
        stmt.setBigDecimal(1, factoryID);
        stmt.setBigDecimal(2, itemID);

        stmt.executeUpdate();
        connection.close();
    }
    public static void insertItemIntoStore(Connection veza, long broj1, long broj2) throws SQLException, IOException{
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO STORE_ITEM (STORE_ID, ITEM_ID) VALUES(?, ?)");
        BigDecimal storeID=new BigDecimal(broj1);
        BigDecimal itemID=new BigDecimal(broj2);
        stmt.setBigDecimal(1, storeID);
        stmt.setBigDecimal(2, itemID);

        stmt.executeUpdate();
        connection.close();
    }
    public static void closeConnection (Connection veza) throws SQLException, IOException{
        veza.close();
    }
}
