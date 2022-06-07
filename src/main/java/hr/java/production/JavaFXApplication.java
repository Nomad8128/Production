package hr.java.production;

import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class JavaFXApplication extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(JavaFXApplication.class.getResource("firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 1000);
        stage.setTitle("Production application (Welcome)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       try{
           Connection veza = Database.connectToDatabase();
           System.out.println("Spojio sam se na bazu");
       } catch(SQLException | IOException ex){
           System.out.println("Pogreska kod spajanja na bazu!");
           ex.printStackTrace();
       }

        launch();
    }
    public static Stage getStage(){
        return mainStage;
    }
}