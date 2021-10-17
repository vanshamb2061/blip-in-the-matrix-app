package movies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class moviesController{

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView poster;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    private Movie movie;

    public void setData(Movie movie){
        this.movie = movie;
        titleLabel.setText(movie.getName());
        genreLabel.setText(movie.getGenre());
        yearLabel.setText(movie.getYear());
        Image image = new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        poster.setImage(image);
    }


    public void mousePressedOnPoster(MouseEvent mouseEvent) throws Exception{
        System.out.println("user clicked on movie, show him movie description");
        Parent root = FXMLLoader.load(getClass().getResource("movieInfo.fxml"));
        Stage movieInfoStage = new Stage();
        movieInfoStage.initStyle(StageStyle.UNDECORATED);
        movieInfoStage.setTitle("Info");
        movieInfoStage.setScene(new Scene(root, 550, 300));
        movieInfoStage.show();
    }

    public void mousePressedOnLike(MouseEvent mouseEvent) {
        System.out.println("user liked this movie, added this to his favorites");
    }

    public void mousePressedOnCancel(MouseEvent mouseEvent) {
        System.out.println("user disliked this movie, remove it from his feed");
    }
}
