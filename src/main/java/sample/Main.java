package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.web.WebView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;/*
import org.json.JSONArray;
import org.json.JSONObject;*/

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/loginPage.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.getIcons().add(new Image("/images/img.png"));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
