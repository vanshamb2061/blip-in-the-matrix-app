package sample;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.Movie;
import movies.moviesController;
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

public class watchListController implements Initializable {
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

    public void updateLikesMovies(List<Movie> likedMoviesArray){
        int col = 0, row = 1;
        try{
            for (Movie movie : likedMoviesArray) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFile/movies.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();
                moviesController movieController = fxmlLoader.getController();
                movieController.setData(movie);
                if (col == 3) {
                    row++;
                    col = 0;
                }
                mainGridPane.add(anchorPane, col++, row);
                //set gridPane width
                mainGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                mainGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                mainGridPane.setMaxWidth(Region.USE_PREF_SIZE);
                //set gridPane height
                mainGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                mainGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                mainGridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
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
            updateLikesMovies(likedMoviesArray);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
