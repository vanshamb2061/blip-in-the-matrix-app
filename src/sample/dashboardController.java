package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.Movie;
import movies.moviesController;
import movies.newMovie;
import movies.newMoviesController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    //code yet to write
    @FXML
    private MenuButton genresMenuBar;
    @FXML
    private Button logOutButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button prevButton;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Label welcomeUserLabel;
    @FXML
    private MenuButton yearsMenuBar;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private GridPane sideGridPane;
    @FXML
    private TextField searchMovies;
    @FXML
    private TextField searchUsername;


    int Current_Pg=1;

    private final List<Movie> movies = new ArrayList<>();

    private List<Movie> getData() throws Exception {
        HttpURLConnection connection = null;
        final String mykey = "201d9cf62a43a21c17cdf0f13ce41312";
        String genres = "Action";
        boolean adult = true;


        URL url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US"
                + "&include_adult=" + adult + "&page="+Current_Pg);
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

        JSONObject jsonObject1 = new JSONObject(result);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");

        List<Movie> movies = new ArrayList<>();
        Movie movie;
        for(int i=0;i<jsonArray.length();i++){
            movie = new Movie();

            movie.setGenre("Action");
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            movie.setJsonObject(jsonObject);

            movie.setGenre(jsonObject.getString("genre_ids"));
            //String id = jsonObject.getString("id");
            //String original_language = jsonObject.getString("original_language");
            movie.setName( jsonObject.getString("original_title"));
            //String overview = jsonObject.getString("overview");
            movie.setImgSrc( "https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));

            String year = jsonObject.getString("release_date");
            movie.setYear(year.split("-")[0]);
            //String video = jsonObject.getString("video");
            //System.out.println(genre_ids+" --> "+id+" --> "+original_language);

            movies.add(movie);
        }
        return movies;
    }

    private final List<newMovie> tryNewMovies = new ArrayList<>();
    private List<newMovie> getTryNewMoviesData() throws Exception {
        HttpURLConnection connection = null;
        final String mykey = "201d9cf62a43a21c17cdf0f13ce41312";
        String genres = "Action";
        boolean adult = true;
        int pg_no=2;

        URL url = new URL("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US"
                + "&include_adult=" + adult + "&page="+pg_no);
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

        JSONObject jsonObject1 = new JSONObject(result);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");

        List<newMovie> tryNewMovies = new ArrayList<>();
        newMovie tryNewMovie;
        for(int i=0;i<jsonArray.length()  && i < 14;i++){
            tryNewMovie = new newMovie();

            tryNewMovie.setGenre("Action");
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            tryNewMovie.setJsonObject(jsonObject);

            tryNewMovie.setGenre(jsonObject.getString("genre_ids"));
            //String id = jsonObject.getString("id");
            //String original_language = jsonObject.getString("original_language");
            tryNewMovie.setName( jsonObject.getString("original_title"));
            //String overview = jsonObject.getString("overview");
            tryNewMovie.setImgSrc( "https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));

            String year = jsonObject.getString("release_date");
            tryNewMovie.setYear(year.split("-")[0]);
            //String video = jsonObject.getString("video");
            //System.out.println(genre_ids+" --> "+id+" --> "+original_language);

            tryNewMovies.add(tryNewMovie);
        }
        return tryNewMovies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            movies.addAll(getData());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int col = 0, row = 1;
        try{
            for (Movie movie : movies) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/movies/movies.fxml"));

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


        try {
            tryNewMovies.addAll(getTryNewMoviesData());
        } catch (Exception e) {
            e.printStackTrace();
        }

        col = 0;
        row = 1;
        try{
            for (newMovie tryNewMovie : tryNewMovies) {
                FXMLLoader sidefxmlLoader = new FXMLLoader();
                sidefxmlLoader.setLocation(getClass().getResource("/movies/newMovies.fxml"));

                AnchorPane anchorPane = sidefxmlLoader.load();
                newMoviesController tryNewMovieController = sidefxmlLoader.getController();
                tryNewMovieController.setData(tryNewMovie);
                if (col == 1) {
                    row++;
                    col = 0;
                }
                sideGridPane.add(anchorPane, col++, row);
                //set sideGridPane width
                sideGridPane.setMinWidth(Region.USE_COMPUTED_SIZE);
                sideGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                sideGridPane.setMaxWidth(Region.USE_PREF_SIZE);
                //set sideGridPane height
                sideGridPane.setMinHeight(Region.USE_COMPUTED_SIZE);
                sideGridPane.setPrefWidth(Region.USE_COMPUTED_SIZE);
                sideGridPane.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(5));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public void logOutButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("loginPage.fxml"));
        Stage loginStage = new Stage();
        loginStage.initStyle(StageStyle.UNDECORATED);
        loginStage.setScene(new Scene(root, 530, 320));
        loginStage.show();
    }
    @FXML
    void keyPressedOnSearchMovies(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            System.out.println("You searched an user having username " + searchMovies.getText());
        }
    }

    @FXML
    void keyPressedOnSearchUsername(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            System.out.println("You searched an user having username " + searchUsername.getText());
        }
    }

    @FXML
    void nextButtonOnAction(ActionEvent event) throws Exception {
        
        if(Current_Pg<10000){
            Current_Pg=Current_Pg+1;
            getData();
        }
    }

    @FXML
    void prevButtonOnAction(ActionEvent event) throws Exception {

        if(Current_Pg>1){
            Current_Pg=Current_Pg-1;
            getData();
        }
    }

    public void mousePressedOnMyProfile(MouseEvent mouseEvent) {
        System.out.println("forwarding you to your profile");
    }

    public void mousePressedOnMovies(MouseEvent mouseEvent) {
        System.out.println("Recommending movies you may like");
    }

    public void mousePressedOnWatchList(MouseEvent mouseEvent) {
        System.out.println("here you'll find all your saved watchlist");
    }

    public void mousePressedOnLiked(MouseEvent mouseEvent) {
        System.out.println("Here you'll find all your liked movies");
    }

    public void mousePressedOnFriends(MouseEvent mouseEvent) {
        System.out.println("Here is the list of all your friends");
    }

    public void mousePressedOnRefreshImageView(MouseEvent mouseEvent) {
        System.out.println("refresh to get new recommendations");
    }

}
