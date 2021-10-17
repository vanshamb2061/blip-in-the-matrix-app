package movies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class moviesController{

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView likeImageView;

    @FXML
    private ImageView poster;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private ImageView cancelImageView;

    private Movie movie;
    public boolean clicked = false;

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
        clicked = !clicked;
        if(clicked == true) {
            System.out.println("user liked this movie, added this to his favorites");
            Image image = new Image(("/images/redLike.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
            ColorAdjust c = new ColorAdjust();
            c.setBrightness(0);
            c.setContrast(0);
            c.setHue(0);
            c.setSaturation(0);
            likeImageView.setEffect(c);
        }else{
            System.out.println("user removed this movie from his favorites");
            Image image = new Image(("/images/unfilledLike.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
            ColorAdjust c = new ColorAdjust();
            c.setBrightness(0);
            c.setContrast(1);
            c.setHue(1);
            c.setSaturation(1);
            likeImageView.setEffect(c);
        }
    }

    public void mousePressedOnCancel(MouseEvent mouseEvent){
        System.out.println("user disliked this movie, remove it from his feed");
    }
}
