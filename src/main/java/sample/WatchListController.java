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
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WatchListController implements Initializable {
    @FXML
    private Button backButton;
    @FXML
    private GridPane mainGridPane;

    Map<String, String > genreIdMap = new HashMap<String, String>();

    Map<String,Integer> genreRatings = new HashMap<String,Integer>();

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
        return stm.executeQuery(sql);
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
            System.out.println(movie.getGenre());

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

        String[] genresArr = {"Action","Comedy","Drama","Fantasy","Horror","Mystery","Romance","Thriller"};
        while(result.next()){
            for(String s:genresArr){
                genreRatings.put(s,genreRatings.get(s)+result.getInt(s));
                System.out.println(s);
            }
        }


        for(Movie movie: likedMoviesArray){
            JSONObject jsonObject = movie.getJsonObject();
            JSONArray arr = jsonObject.getJSONArray("genres");
            for (int i = 0; i < arr.length(); i++)
            {
                String s = arr.getJSONObject(i).getString("name");
                if(genreIdMap.get(s) != null){
                    System.out.println("Genre: " + s + " " + genreIdMap.get(s));
                    movie.setGenre(genreIdMap.get(s));
                    genreRatings.put(s,genreRatings.get(s)+5);
                }
            }
        }


        for(Map.Entry<String,Integer> entry: genreRatings.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        String query="UPDATE genre SET Action=?, Comedy=?, Drama=?, Fantasy=?, Horror=?, Mystery=?, Romance=?, Thriller=? WHERE Username=?";
        PreparedStatement preStat = connection.prepareStatement(query);
        preStat.setInt(1,genreRatings.get("Action"));
        preStat.setInt(2,genreRatings.get("Comedy"));
        preStat.setInt(3,genreRatings.get("Drama"));
        preStat.setInt(4,genreRatings.get("Fantasy"));
        preStat.setInt(5,genreRatings.get("Horror"));
        preStat.setInt(6,genreRatings.get("Mystery"));
        preStat.setInt(7,genreRatings.get("Romance"));
        preStat.setInt(8,genreRatings.get("Thriller"));
        preStat.setString(9,"ambashtavansh");
        preStat.executeUpdate();
}


    public void initialize(URL url, ResourceBundle resourceBundle) {
        //hashmap initialization
        genreIdMap.put("Action","28");
        genreIdMap.put("Comedy","35");
        genreIdMap.put("Drama","18");
        genreIdMap.put("Crime","80");
        genreIdMap.put("Fantasy","14");
        genreIdMap.put("Horror","27");
        genreIdMap.put("Mystery","9648");
        genreIdMap.put("Romance","10749");
        genreIdMap.put("Thriller","53");


        genreRatings.put("Action",0);
        genreRatings.put("Comedy",0);
        genreRatings.put("Drama",0);
        genreRatings.put("Crime",0);
        genreRatings.put("Fantasy",0);
        genreRatings.put("Horror",0);
        genreRatings.put("Mystery",0);
        genreRatings.put("Romance",0);
        genreRatings.put("Thriller",0);

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
