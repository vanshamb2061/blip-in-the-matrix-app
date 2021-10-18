package movies;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class playTrailer implements Initializable {

    @FXML
    private Button backwardButton;
    @FXML
    private Button forwardButton;
    @FXML
    private MediaView mediaView;
    @FXML
    private ToggleButton playPauseButton;

    String path = "avengersEndgameTrailer.mkv";
    private Media media;
    private MediaPlayer mediaPlayer;
    private File file;

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

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     /*   file = new File(path);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
     */
    }

}