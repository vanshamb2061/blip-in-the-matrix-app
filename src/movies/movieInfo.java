package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.Movie;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class movieInfo implements Initializable {

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

    private boolean clicked = false;
    @FXML
    void mousePressedOnAddToFavorites(MouseEvent event) {
        System.out.println("movie added to the favorites");
        clicked = !clicked;
        if(clicked == true) {
            System.out.println("movie added to the favorites");
            Image image = new Image(("/images/blueLove.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
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

    @FXML
    void mousePressedOnPlayTrailer(MouseEvent event) throws Exception{
        /*System.out.println("Please wait..... we're playing trailer for you.");

        Parent root = FXMLLoader.load(getClass().getResource("playTrailer.fxml"));
        Stage playTrailerStage = new Stage();
        playTrailerStage.initStyle(StageStyle.DECORATED);
        playTrailerStage.setTitle("play Trailer");
        playTrailerStage.setScene(new Scene(root, 900, 500));
        playTrailerStage.show();

         */
    }
    public void setEverythingInMovieInfo(Movie movie) throws Exception{
        JSONObject jsonObject = movie.getJsonObject();
        System.out.println(jsonObject);
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
    public void setEverythingInMovieInfo(newMovie movie) throws Exception{
        JSONObject jsonObject = movie.getJsonObject();
        System.out.println(jsonObject);
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
    @FXML
    void mousePressedOnCancel2(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
