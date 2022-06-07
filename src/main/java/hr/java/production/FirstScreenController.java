package hr.java.production;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FirstScreenController {
    public void showCategorySearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("categorySearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showItemSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("itemSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showFactorySearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("factorySearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showStoreSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("storeSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddCategoryScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewCategory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddItemScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewItem.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddFactoryScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewFactory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddStoreScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewStore.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddItemFactoryScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewItemFactory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddItemStoreScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewItemStore.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAllItemsFactoryScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("allItemsInFactory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAllItemsStoreScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("allItemsInStore.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddAddressScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addNewAddress.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
    public void showAddressSearchScreen(){
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("addressSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaFXApplication.getStage().setTitle("Production application");
        JavaFXApplication.getStage().setScene(scene);
        JavaFXApplication.getStage().show();
    }
}
