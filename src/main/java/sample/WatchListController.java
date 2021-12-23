package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.Movie;
import movies.MoviesController;
import org.json.JSONArray;
import org.json.JSONObject;
import users.User;

import javax.xml.transform.Result;
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

public class WatchListController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private GridPane mainGridPane;

    Map<String, String > genreIdMap = new HashMap<String, String>();

    public void backButtonOnAction(ActionEvent e){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
    public ResultSet findLikedMoviesInDB() throws Exception{
        //Method to find the likedMovies IDs from DB for particular user & return ResultSet
        System.out.println("here you'll find all your saved watchlist");
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection connection = DriverManager.getConnection(url, "root", "");

        String username = "ambashtavansh";

        Statement stm = connection.createStatement();

        String sql = "select * from movie where Username='" + username + "'";
        ResultSet result = stm.executeQuery(sql);
        return result;
    }
    public List<Movie> searchLikedMovies(ResultSet res) throws Exception{
        //Method to take the IDs of all the liked movies and run API calls to get the movies & return the movies
        HttpURLConnection connection = null;
        final String mykey = "52dbdefafcc6e3911db1a3409fc33e8a";
        boolean adult = true;
        List<Movie> likedMoviesArray = new ArrayList<>();

        while (res.next()) {
            String movieid = res.getString("MovieID");
            String usern = res.getString("Username");
            System.out.println("   "+movieid+" "+usern);
            URL url = new URL("https://api.themoviedb.org/3/movie/" + movieid + "?api_key=" + mykey + "&language=en-US");
            System.out.println(url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r");
            }
            reader.close();
            String result = response.toString();

            JSONObject jsonObject = new JSONObject(result);
            System.out.println(jsonObject);
            Movie movie;
            movie = new Movie();
            movie.setGenre("Action");

            movie.setJsonObject(jsonObject);
            int genreLength = jsonObject.getString("genres").length();
            String str = jsonObject.getString("genres").substring(1, genreLength-2);
            String genreString[] = str.split(",");
            movie.setGenre("Other");
            for(String s : genreString){
                if(genreIdMap.get(s) != null){
                    movie.setGenre(genreIdMap.get(s));
                    break;
                }
            }

            movie.setName( jsonObject.getString("original_title"));
            movie.setImgSrc( "https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));

            String year = jsonObject.getString("release_date");
            movie.setYear(year.split("-")[0]);

            likedMoviesArray.add(movie);
        }
        return likedMoviesArray;
    }

    public void updateLikedMovies(List<Movie> likedMoviesArray){
        //Method to display all the liked movies
        AtomicInteger col = new AtomicInteger();
        int row = 1;
        try{
            for (Movie movie : likedMoviesArray) {
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

    public int changeRating(int currRating, boolean like){
        if(like)
            currRating+=5;
        else
            currRating-=2;
        return Math.max(currRating, 0);
    }
    public void updateRatings(List<Movie> likedMoviesArray) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection connection = DriverManager.getConnection(url, "root", "");
        String username = "ambashtavansh";
        Statement stm = connection.createStatement();
        String sql = "select * from genre where Username='" + username + "'";
        ResultSet result = stm.executeQuery(sql);

        for(Movie movie: likedMoviesArray){
            System.out.println(movie.getGenre());
        }
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //hashmap initialization
        genreIdMap.put("28","Action");
        genreIdMap.put("35","Comedy");
        genreIdMap.put("18","Drama");
        genreIdMap.put("80","Crime");
        genreIdMap.put("14","Fantasy");
        genreIdMap.put("27","Horror");
        genreIdMap.put("9648","Mystery");
        genreIdMap.put("10749","Romance");
        genreIdMap.put("53","Thriller");
        try {
            ResultSet res = findLikedMoviesInDB();
            List<Movie> likedMoviesArray = searchLikedMovies(res);
            updateLikedMovies(likedMoviesArray);
            updateRatings(likedMoviesArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
