package movies;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.scene.web.*;

public class PlayTrailer implements Initializable {

    public WebView webPlayer;
    @FXML
    private Button backwardButton;
    @FXML
    private Button forwardButton;
    @FXML
    private ToggleButton playPauseButton;
    @FXML
    private ImageView cancelImageView;
    @FXML

    private String videoName;
    @FXML
    void backwardButtonOnAction(ActionEvent event) {

    }
    @FXML
    void forwardButtonOnAction(ActionEvent event) {

    }
    @FXML
    void playPauseButtonOnAction(ActionEvent event) {

    }
    @FXML
    void mousePressedOnCancelImageView(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("playing trailer in web format");

        //encountering error when including webview find out why this is happening.............


        /*Stage stage = new Stage();
        WebView webview = new WebView();
        webview.getEngine().load(
                "https://youtu.be/BJXl0kO0YC0"
        );
        webview.setPrefSize(640, 390);

        stage.setScene(new Scene(webview));
        stage.show();*/


/*        Stage stage = new Stage();
        MediaView mediaView = new MediaView();
        Media media = new Media("https://www.youtube.com/watch?v=6JnN1DmbqoU");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        stage.setScene(new Scene(mediaView));
        stage.show();*/

    }

}