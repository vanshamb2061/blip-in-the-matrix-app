package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import apiKeys.Services;
import sample.LikeController;
import sample.PlayListController;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

//Class for movie info

public class MovieInfo implements Initializable {

    @FXML
    private ImageView backgroundImageView;
    @FXML
    private ImageView cancelImageView;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Label imdbRatingsLabel;
    @FXML
    private ImageView likeImageView;
    @FXML
    private Label movieLabel;
    @FXML
    private ImageView addImageView;

    Movie movieObj;

    //this is used to get apiKeys which is store secretly
    Services serviceObject = new Services();
    private boolean isClickedLike = false;
    private boolean isClickedPlus = false;

    // this is used to add movies to in favorite list of user
    @FXML
    void mousePressedOnAddToFavorites(MouseEvent event) throws Exception {
        //Method to handle mouseevent when user hits the "like"
        System.out.println("movie added to the favorites");
        isClickedLike = !isClickedLike;
        if(isClickedLike == true) {
            System.out.println("movie added to the favorites");
            Image image = new Image(("/images/blueLove.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);

            LikeController likeController = new LikeController();
            likeController.addLikedMovietoDB(movieObj);
        }else{
            System.out.println("movie removed from his favorites");
            Image image = new Image(("/images/unfilledLike.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
        }
        DropShadow d = new DropShadow();
        d.setSpread(0.66);
        likeImageView.setEffect(d);
    }

    //this is used to play trailer it creates a new scene and play trailer
    // this is done using webView
    @FXML
    void mousePressedOnPlayTrailer(MouseEvent event) throws Exception{
        //Method to play trailer on mousevent when user hits play
        HttpURLConnection connection = null;
        final String mykey = serviceObject.API_KEY;
        boolean adult = true;

        URL url = new URL("http://api.themoviedb.org/3/movie/" + movieObj.getId() + "/videos?api_key=" + mykey);
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

        JSONObject jsonObject1 = new JSONObject(result);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        System.out.println("https://www.youtube.com/embed/" + jsonObject.getString("key"));
        String video = "https://www.youtube.com/embed/" + jsonObject.getString("key");
        System.out.println("Please wait..... we're playing trailer for you.");
        System.out.println(video);
        System.out.println(movieObj.getId());


        Stage trailerStage = new Stage();
        trailerStage.setTitle("Trailer");
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(video);
        Scene scene = new Scene(webView, 930, 545);
        trailerStage.setScene(scene);
        trailerStage.initModality(Modality.APPLICATION_MODAL);
        trailerStage.initOwner(((Node)event.getSource()).getScene().getWindow() );
        trailerStage.show();




/*
        System.out.println(movieObj.getJsonObject());
        JSONObject jsonObject = movieObj.getJsonObject();
        System.out.println(jsonObject);
        JSONObject js = new JSONObject("https://api.themoviedb.org/3/movie/297762/videos?api_key=201d9cf62a43a21c17cdf0f13ce41312&language=en-US");
*/
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/playTrailer.fxml"));
        Parent root = loader.load();
        Stage movieInfoStage = new Stage();
        movieInfoStage.initStyle(StageStyle.UNDECORATED);
        movieInfoStage.setTitle("Info");
        movieInfoStage.setScene(new Scene(root, 900, 500));
        movieInfoStage.getIcons().add(new Image("/images/img.png"));
        movieInfoStage.show();*/
/*
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/playTrailer.fxml"));
        Parent root = loader.load();
        *//*
        playTrailer playTrailerController = loader.getController();
        playTrailerController.playingTrailer(video);*//*
        Stage playTrailerStage = new Stage();
        playTrailerStage.initStyle(StageStyle.UNDECORATED);
        //playTrailerStage.setTitle("play Trailer");
        playTrailerStage.setScene(new Scene(root, 900, 500));
        *//*playTrailerStage.getIcons().add(new Image("/images/img.png"));*//*
        playTrailerStage.show();*/

    }

    //this function is called from dashboard to set the bakcground image name and everything which is shown on scene
    //this also sends the movieObject from movies controller when clicked
    public void setEverythingInMovieInfo(Movie movie) throws Exception{
        //Method to set everything for movie object
        movieObj = movie;
        JSONObject jsonObject = movie.getJsonObject();
        //System.out.println(jsonObject);
        Image image = new Image("https://image.tmdb.org/t/p/w500"+jsonObject.getString("backdrop_path"));
        backgroundImageView.setImage(image);
        //movie.setGenre(jsonObject.getString("genre_ids"));
        //String id = jsonObject.getString("id");
        //String original_language = jsonObject.getString("original_language");
        movieLabel.setText( jsonObject.getString("original_title"));
        String overview = jsonObject.getString("overview");
        descriptionTextArea.setText(overview);
        //String video = jsonObject.getString("video");
    }


    // this is used to close the movieINfo page
    @FXML
    void mousePressedOnCancel2(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }

    //this fun activate when we click add to watchlist this is used to store the clicked movie into watchlist data
    @FXML
    void mousePressedOnAddToWatchList(MouseEvent event) throws Exception {

        isClickedPlus = !isClickedPlus;
        if(isClickedPlus == true) {
            System.out.println("movie added to watchList");
            Image image = new Image(("images/filledPlusIcon.png"));
            addImageView.setImage(image);
            addImageView.setFitWidth(29);
            addImageView.setFitHeight(30);
        }else{
            System.out.println("movie removed from watchList");
            Image image = new Image("images/unFilledPlusIcon.png");
            addImageView.setImage(image);
            addImageView.setFitWidth(29);
            addImageView.setFitHeight(30);
        }
        DropShadow d = new DropShadow();
        d.setSpread(0.66);
        addImageView.setEffect(d);
        System.out.println(movieObj.getName());
        PlayListController playListController = new PlayListController();
        playListController.addMovietoPlaylist(movieObj);

    }

    //this function is used to initialize when first start
    //currently it is empty
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}