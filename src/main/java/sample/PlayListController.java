package sample;
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
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayListController implements Initializable {

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

    public void addMovietoPlaylist(Movie movieobj) throws Exception {
        //Add movie to playlist table in DB
        System.out.println("addMovietoPlaylist Entered");
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection myConn = DriverManager.getConnection(url, "root", ""); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement();
        JSONObject jsonObject = movieobj.getJsonObject();
        int movieid = jsonObject.getInt("id");
        String username = "ambashtavansh";
        String query="INSERT INTO `playlist`(`MovieID`, `Username`) VALUES (?,?)";
        PreparedStatement preStat = myConn.prepareStatement(query);
        System.out.println("Query written");
            preStat.setInt(1,movieid);
            preStat.setString(2,username);
            preStat.executeUpdate();
            System.out.println("movie added to playlist db");
            System.out.println(movieid);
//            List likeController = new PlayListController();
//            likeController.updateRatings(movieobj);
    }

    public ResultSet findPlaylistMoviesInDB() throws Exception{
        //Method to find the likedMovies IDs from DB for particular user & return ResultSet
        System.out.println("here you'll find all your saved playlist");
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection connection = DriverManager.getConnection(url, "root", "");

        String username = "ambashtavansh";

        Statement stm = connection.createStatement();

        String sql = "select * from playlist where Username='" + username + "'";
        return stm.executeQuery(sql);
    }
    public List<Movie> searchPlaylist(ResultSet res) throws Exception{
        //Method to take the IDs of all the playlist movies and run API calls to get the movies & return the movies
        HttpURLConnection connection = null;
        final String mykey = "52dbdefafcc6e3911db1a3409fc33e8a";
        boolean adult = true;
        List<Movie> playlistArr = new ArrayList<>();

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
            movie.setJsonObject(jsonObject);
            movie.setName( jsonObject.getString("original_title"));
            movie.setImgSrc( "https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));
            String year = jsonObject.getString("release_date");
            movie.setYear(year.split("-")[0]);
            playlistArr.add(movie);
        }
        return playlistArr;
    }

    public void updatePlaylistMovies(List<Movie> playlistArr){
        //Method to display all the playlist movies
        AtomicInteger col = new AtomicInteger();
        int row = 1;
        try{
            for (Movie movie : playlistArr) {
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
        try {
            ResultSet res = findPlaylistMoviesInDB();
            List<Movie> playlistArr = searchPlaylist(res);
            updatePlaylistMovies(playlistArr);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


