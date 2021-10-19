package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
  // /*
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 530, 320));
        primaryStage.show();
//*/
        //added this line to access dashboard instant
      /*
        Parent root = FXMLLoader.load(getClass().getResource("dashboardController.fxml"));
        Stage dashboardStage = new Stage();
        dashboardStage.initStyle(StageStyle.DECORATED);
        dashboardStage.setTitle("watchlist");
        dashboardStage.setScene(new Scene(root, 900, 700));
        dashboardStage.show();
*/
/*
        Parent root = FXMLLoader.load(getClass().getResource("/movies/playTrailer.fxml"));
        Stage playTrailerStage = new Stage();
        playTrailerStage.initStyle(StageStyle.DECORATED);
        playTrailerStage.setTitle("play Trailer");
        playTrailerStage.setScene(new Scene(root, 900, 500));
        playTrailerStage.show();

 */
/*
        primaryStage.initStyle(StageStyle.DECORATED);
        WebView webView = new WebView();
        webView.getEngine().load("https://www.youtube.com/watch?v=UT5F9AXjwhg");
        webView.setPrefSize(840, 690);
        primaryStage.setScene(new Scene(webView));
        primaryStage.show();

 */
    }



    public static void main(String[] args) {
        launch(args);
    }
}
