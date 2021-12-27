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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.Movie;
import movies.MoviesController;
import movies.NewMovie;
import movies.NewMoviesController;
import org.json.JSONArray;
import org.json.JSONObject;
import apiKeys.Services;
import apiKeys.GlobalData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

//Method to handle the following:
//  1. All mouseevents on dashboard
//  2. Fetching movies using TMDB api calls
//  3. Loading and displaying movies

public class DashboardController implements Initializable {
    //code yet to write
    @FXML
    private MenuButton genresMenuButton;
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
    public FlowPane mainFlowPane;
    @FXML
    private FlowPane sideFlowPane;
    @FXML
    private TextField searchMovies;
    @FXML
    private TextField searchUsername;
    Services serviceObject = new Services();

    Map<String, String > genreIdMap = new HashMap<String, String>();
    int Current_Pg=1;
    int searchPg = 1;

    public List<Movie> getData(String tmdbURL) throws Exception {
        //method to fetch data from TMDB API, gets String URL as arguement
        HttpURLConnection connection = null;

        URL url = new URL(tmdbURL);
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
        for(int i=0;i<jsonArray.length() && i < 20;i++){
            movie = new Movie();

            movie.setGenre("Action");
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            movie.setJsonObject(jsonObject);
            int genreLength = jsonObject.getString("genre_ids").length();
            /*String str = jsonObject.getString("genre_ids").substring(1, genreLength-2);
            String genreString[] = str.split(",");*/
            movie.setGenre("Other");
            /*for(String s : genreString){
                if(genreIdMap.get(s) != null){
                    movie.setGenre(genreIdMap.get(s));
                    break;
                }
            }*/
            //String id = jsonObject.getString("id");
            //String original_language = jsonObject.getString("original_language");
            movie.setName( jsonObject.getString("original_title"));
            //String overview = jsonObject.getString("overview");
            movie.setImgSrc( "https://image.tmdb.org/t/p/w500"+jsonObject.getString("poster_path"));

            String year = jsonObject.getString("release_date");
            movie.setYear(year.split("-")[0]);
            movie.setId(jsonObject.getInt("id"));
            //System.out.println(genre_ids+" --> "+id+" --> "+original_language);

            movies.add(movie);
        }
        return movies;
    }


    private List<Movie> movies ;
    public void updateMoviesOnDashboard(String tmdbURL){
        //Display the movies on the dashboard
        try {
            boolean adult = false;
            final String mykey = serviceObject.API_KEY;
            movies.addAll(getData(tmdbURL));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            for (Movie movie : movies) {
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
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private final List<Movie> tryNewMovies = new ArrayList<>();
    public void updateSideMovieOnDashboard(){
        //Method to display movies on the side on dashboard
        try {
            final String mykey = serviceObject.API_KEY;
            boolean adult = true;
            //tryNewMovies.addAll(getData("https://api.themoviedb.org/3/movie/now_playing?api_key=" + mykey + "&language=en-US&page=1"));
            tryNewMovies.addAll(getData("https://api.themoviedb.org/3/movie/upcoming?api_key=" + mykey + "&language=en-US&page=1"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            for (Movie tryNewMovie : tryNewMovies) {
                FXMLLoader sidefxmlLoader = new FXMLLoader();
                sidefxmlLoader.setLocation(getClass().getResource("/fxmlFile/newMovies.fxml"));

                VBox vBox = sidefxmlLoader.load();
                NewMoviesController tryNewMovieController = sidefxmlLoader.getController();
                tryNewMovieController.setData(tryNewMovie);

                Platform.runLater(()->{
                    sideFlowPane.getChildren().add(vBox);
                });
                FlowPane.setMargin(vBox, new Insets(15));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private List<Movie> searchMoviesArray ;
    public void updateMoviesOnDashboardOnSearch(){
        //Method to handle search on dashboard
        try {
            final String myKey = serviceObject.API_KEY;
            boolean adult = true;
            searchMoviesArray.addAll(getData("https://api.themoviedb.org/3/search/movie?api_key=" + myKey + "&language=en-US&page=" + searchPg + "&include_adult="+ adult + "&query=" + searchMovies.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{

            for (Movie movie : searchMoviesArray) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/fxmlFile/movies.fxml"));

                VBox anchorPane = fxmlLoader.load();
                MoviesController movieController = fxmlLoader.getController();
                movieController.setData(movie);

                Platform.runLater(()->{
                    mainFlowPane.getChildren().add(anchorPane);
                });
                FlowPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateMoviesByGenre(String id){
        //Method to update movies by genre, takes string id as arguement
        movies = new ArrayList<>();
        mainFlowPane.getChildren().clear();
        new Thread(new Runnable() {
            @Override public void run() {
                boolean adult = false;
                final String mykey = serviceObject.API_KEY;
                System.out.println(id);
                updateMoviesOnDashboard("http://api.themoviedb.org/3/genre/"+id+ "/movies?api_key=" + mykey);
                //updateMoviesOnDashboard("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US&sort_by=popularity.asc&include_adult=false&include_video=false&page=1&&with_genres=" + id);

            }
        }).start();

    }

    @FXML
    void keyPressedOnSearchMovies(KeyEvent event) throws Exception{
        //Method to handle keyevent when key pressed on search movies
        if(event.getCode().equals(KeyCode.ENTER)){
            searchMoviesArray = new ArrayList<>();
            System.out.println(searchMovies.getText()=="");
            if(searchMovies.getText() == ""){
                movies = new ArrayList<>();
                mainFlowPane.getChildren().clear();
                new Thread(new Runnable() {
                    @Override public void run() {
                        boolean adult = false;
                        final String mykey = serviceObject.API_KEY;
                        updateMoviesOnDashboard("https://api.themoviedb.org/3/movie/popular?api_key="+ mykey +
                                "&language=en-US&page=" + Current_Pg);
                    }
                }).start();
            }else{
                System.out.println("You searched movie having name " + searchMovies.getText());
                mainFlowPane.getChildren().clear();
                new Thread(new Runnable() {
                    @Override public void run() {
                        updateMoviesOnDashboardOnSearch();
                    }
                }).start();
            }

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
        //Method for when the next Button is clicked
        Current_Pg = Current_Pg + 1;
        prevButton.setDisable(false);
        movies = new ArrayList<>();
        mainFlowPane.getChildren().clear();
        new Thread(new Runnable() {
            @Override public void run() {
                boolean adult = false;
                final String mykey = serviceObject.API_KEY;
                updateMoviesOnDashboard("https://api.themoviedb.org/3/movie/popular?api_key="+ mykey +
                        "&language=en-US&page=" + Current_Pg);

            }
        }).start();
    }
    @FXML
    void prevButtonOnAction(ActionEvent event) throws Exception{
        //Method for when previous button is clicked
        Current_Pg = Current_Pg - 1;
        if(Current_Pg == 1){
            prevButton.setDisable(true);
        }
        movies = new ArrayList<>();
        mainFlowPane.getChildren().clear();
        new Thread(new Runnable() {
            @Override public void run() {
                boolean adult = false;
                final String mykey = serviceObject.API_KEY;
                updateMoviesOnDashboard("https://api.themoviedb.org/3/movie/popular?api_key="+ mykey +
                        "&language=en-US&page=" + Current_Pg);
            }
        }).start();
    }

    public void mousePressedOnMyProfile(MouseEvent mouseEvent) {
        System.out.println("forwarding you to your profile");
    }

    public void mousePressedOnMovies(MouseEvent mouseEvent) {
        System.out.println("Recommending movies you may like");
    }

    public void mousePressedOnWatchList(MouseEvent mouseEvent) throws IOException {
        //Method to load new stage when watchlist is clicked on sidebar
        System.out.println("here you'll find all your saved watchlist");
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/watchListPage.fxml"));
        Stage watchListStage = new Stage();
        watchListStage.setScene(new Scene(root));
        watchListStage.show();
    }

    public void mousePressedOnFeed(MouseEvent mouseEvent) throws Exception {
        //Method to load new stage when feed is clicked on sidebar
        System.out.println("Here you'll find all your feed movies");
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/feedPage.fxml"));
        Stage FeedStage = new Stage();
        FeedStage.setScene(new Scene(root));
        FeedStage.show();
    }

    public void mousePressedOnPlaylist(MouseEvent mouseEvent) throws Exception {
        //Method to load new stage when playlist is clicked on sidebar
        System.out.println("Here you'll find all your playlist movies");
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/playlistPage.fxml"));
        Stage FeedStage = new Stage();
        FeedStage.setScene(new Scene(root));
        FeedStage.show();
    }

    public void mousePressedOnFriends(MouseEvent mouseEvent) {
        System.out.println("Here is the list of all your friends");
    }

    public void mousePressedOnRefreshImageView(MouseEvent mouseEvent)
    {   //Method to refresh
        System.out.println("refresh to get new recommendations");
        mainFlowPane.getChildren().clear();
        new Thread(new Runnable() {
            @Override public void run() {
                boolean adult = false;
                final String mykey = serviceObject.API_KEY;
                System.out.println("Trying to run updateMovies");
                updateMoviesOnDashboard("https://api.themoviedb.org/3/movie/popular?api_key="+ mykey +
                        "&language=en-US&page=" + Current_Pg);
                System.out.println("updateMovies Successful");
            }
        }).start();
    }

    public void setUserNameInDashboardController(String text) {
        welcomeUserLabel.setText("Welcome " + text);
    }

    public void logOutButtonOnAction(ActionEvent event) throws IOException {
        //Method to log out
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout!");
        alert.setHeaderText("You're about to logout");
        alert.setContentText("Do you want to exit?");

        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/images/img.png"));

        if(alert.showAndWait().get()==ButtonType.OK) {
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            System.out.println("You successfully logged out!");
            stage.close();
            GlobalData.setUserId("");
            GlobalData.setUserAge(0);
            Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/loginPage.fxml"));
            Stage loginStage = new Stage();
            loginStage.initStyle(StageStyle.UNDECORATED);
            loginStage.setScene(new Scene(root, 600, 350));
            loginStage.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    /*    ThreadClasses.RenderDashboardMovies setDashboardMovies = new ThreadClasses.RenderDashboardMovies(this);
        Thread thread = new Thread(setDashboardMovies);
*/
        movies = new ArrayList<>();
        Thread thread1 = new Thread(new Runnable() {
            @Override public void run() {
                boolean adult = true;
                final String mykey = serviceObject.API_KEY;
                /*updateSideMovieOnDashboard();*/
                /*updateMoviesOnDashboard("https://api.themoviedb.org/3/discover/movie?api_key=" + mykey + "&language=en-US"
                        + "&include_adult=" + adult + "&page="+Current_Pg);*/
                updateMoviesOnDashboard("https://api.themoviedb.org/3/movie/popular?api_key="+ mykey +
                        "&language=en-US&page=" + Current_Pg);
                System.out.println("updateMovies Successful");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override public void run() {
                updateSideMovieOnDashboard();
            }
        });

        thread1.start();
        thread2.start();


        ///this is to check working of global data;
        //this is the reason for dashboard not updating
       /* GlobalData.setUserId("setting the value of userID");
        System.out.println(GlobalData.getUserId());*/


        prevButton.setDisable(true);
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

        movies = new ArrayList<>();


        genreIdMap.forEach((id, genre) -> {
            System.out.println(id + " : " + (genre));
            MenuItem menuItem = new MenuItem(genre);
            menuItem.addEventHandler(ActionEvent.ACTION, action ->{
                System.out.println(menuItem.getText() + " : is selected");
                updateMoviesByGenre(id);
            });
            genresMenuButton.getItems().add(menuItem);

        });
    }

}
