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

import java.io.IOException;
import java.net.URL;
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

    private final List<Movie> movies = new ArrayList<>();
    private List<Movie> getData(){
        List<Movie> movies = new ArrayList<>();
        Movie movie;
        for(int i = 0; i < 21; i++){
            movie = new Movie();
            movie.setName("Avengers Endgame");
            movie.setImgSrc("/images/avengersEndgame.jpg");
            movie.setGenre("Action");
            movie.setYear("2019");
            movies.add(movie);
        }
        return movies;
    }

    private final List<newMovie> tryNewMovies = new ArrayList<>();
    private List<newMovie> getTryNewMoviesData(){
        List<newMovie> tryNewMovies = new ArrayList<>();
        newMovie tryNewMovie;
        for(int i = 0; i < 5; i++){
            tryNewMovie = new newMovie();
            tryNewMovie.setName("Avengers Endgame");
            tryNewMovie.setImgSrc("/images/avengersEndgame.jpg");
            tryNewMovie.setGenre("Action");
            tryNewMovie.setYear("2019");
            tryNewMovies.add(tryNewMovie);
        }
        return tryNewMovies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movies.addAll(getData());
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



        tryNewMovies.addAll(getTryNewMoviesData());
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
