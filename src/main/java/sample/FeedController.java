package sample;

import apiKeys.Services;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
    private GridPane mainGridPane;

    Map<String, String > genreIdMap = new HashMap<String, String>();

    Map<String,Integer> genreRatings = new HashMap<String,Integer>();

    List<Movie> feedMovies = new ArrayList<Movie>();

    Services serviceObject = new Services();

    public void backButtonOnAction(ActionEvent e){
        //Method to handle event when back button is clicked
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
    public List<Movie> searchFeed() throws Exception{
        //Method to fetch movies for feed using API
        String username = "ambashtavansh";

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
        AtomicInteger col = new AtomicInteger();
        int row = 1;
        try{
            for (Movie movie : feedMovies) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFile/movies.fxml"));

                VBox anchorPane = fxmlLoader.load();
                MoviesController movieController = fxmlLoader.getController();
                movieController.setData(movie);

                ColumnConstraints colConstraint = new ColumnConstraints();
                colConstraint.setHgrow(Priority.SOMETIMES);

                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(Priority.SOMETIMES);

                mainGridPane.getColumnConstraints().add(colConstraint);
                mainGridPane.getRowConstraints().add(rowConstraints);

                if (col.get() == 4) {
                    row++;
                    col.set(0);

                }
                int finalRow = row;
                Platform.runLater(()->{
                    mainGridPane.add(anchorPane, col.getAndIncrement(), finalRow);
                    //set gridPane width
                    mainGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                    mainGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    mainGridPane.setMaxWidth(Region.USE_PREF_SIZE);
                    mainGridPane.setFillWidth(anchorPane, true);
                    //set gridPane height
                    mainGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                    mainGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    mainGridPane.setMaxHeight(Region.USE_PREF_SIZE);
                    mainGridPane.setFillHeight(anchorPane, true);
                });

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        GenreMapController genreMapController = new GenreMapController();
        try {
            genreRatings = genreMapController.getMap("ambashtavansh");
            genreIdMap = genreMapController.getStringtoIDMap();
            searchFeed();
            updateFeedMovies();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
