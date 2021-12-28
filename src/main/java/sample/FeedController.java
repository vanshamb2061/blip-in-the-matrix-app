package sample;

import apiKeys.GlobalData;
import apiKeys.Services;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import movies.Movie;
import movies.MoviesController;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//Method to do the following:
//  1. Decide preferred genres on the basis of ratings in DB
//  2. Fetch movies for those genres using APIs
//  3. Display the movies

public class FeedController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private Label welcomeUserLabel;

    Map<String, String > genreIdMap = new HashMap<String, String>();

    Map<String,Integer> genreRatings = new HashMap<String,Integer>();

    List<Movie> feedMovies = new ArrayList<Movie>();

    Services serviceObject = new Services();

    String username = GlobalData.getUserId();

    public void backButtonOnAction(ActionEvent e){
        //Method to handle event when back button is clicked
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
    public List<Movie> searchFeed() throws Exception{
        //Method to fetch movies for feed using API

        List<String> genreArr = new ArrayList<String>();
        for (Map.Entry<String,Integer> entry : genreRatings.entrySet())
        {
            if(entry.getValue()>=15)
                genreArr.add(genreIdMap.get(entry.getKey()));
        }
        int currPage = 1;
        HttpURLConnection connection = null;
        for(String genre:genreArr)
        {
            System.out.println(genre);
            final String mykey = serviceObject.API_KEY;
            String tmdbURL = ("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US" + "&with_genres=" + genre + "&page=" + currPage);
            System.out.println(tmdbURL);
            DashboardController dashboardController = new DashboardController();
            feedMovies = dashboardController.getData(tmdbURL);
        }
        System.out.println("Size: " + feedMovies.size());
//        WatchListController watchListController = new WatchListController();
//        watchListController.updateLikedMovies(feedMovies);
//        updateFeedMovies(feedMovies);
        return feedMovies;
    }

    public void updateFeedMovies(){
        //Method to display all the liked movies

        try{
            for (Movie movie : feedMovies) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFile/movies.fxml"));

                VBox anchorPane = fxmlLoader.load();
                MoviesController movieController = fxmlLoader.getController();
                movieController.setData(movie);

                Platform.runLater(()->{
                    mainFlowPane.getChildren().add(anchorPane);
                });

                FlowPane.setMargin(anchorPane, new Insets(15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenreMapController genreMapController = new GenreMapController();
        try {
            welcomeUserLabel.setText("Welcome to your Feed");
            genreRatings = genreMapController.getMap(username);
            genreIdMap = genreMapController.getStringtoIDMap();
            searchFeed();

            mainFlowPane.getChildren().clear();
            new Thread(new Runnable() {
                @Override public void run() {
                    boolean adult = false;
                    final String mykey = serviceObject.API_KEY;
                    updateFeedMovies();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
