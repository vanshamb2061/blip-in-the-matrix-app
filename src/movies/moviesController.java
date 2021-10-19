package movies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movies.movieInfo;
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

    protected Movie movie;
    public boolean clicked = false;

    public void setData(Movie movie){
        this.movie = movie;
        titleLabel.setText(movie.getName());
        genreLabel.setText(movie.getGenre());
        yearLabel.setText(movie.getYear());
        movie.setJsonObject(movie.getJsonObject());

        // this one is to load images from local data
       // Image image = new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        //and this one is to load images from url go to dashboardController.java to change location/url.
        Image image = new Image(movie.getImgSrc());
        poster.setImage(image);
    }


    public void mousePressedOnPoster(MouseEvent mouseEvent) throws Exception{
        System.out.println("user clicked on movie, show him movie description");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("movieInfo.fxml"));
        Parent root = loader.load();
        movieInfo movieInfoController = loader.getController();
        movieInfoController.setEverythingInMovieInfo(movie);
        Stage movieInfoStage = new Stage();
        movieInfoStage.initStyle(StageStyle.UNDECORATED);
        movieInfoStage.setTitle("Info");
        movieInfoStage.setScene(new Scene(root, 600, 340));
        movieInfoStage.getIcons().add(new Image("/images/img.png"));
        movieInfoStage.show();
    }

    public void mousePressedOnLike(MouseEvent mouseEvent) {
        clicked = !clicked;
        if(clicked == true) {
            System.out.println("user liked this movie, added this to his favorites");
            Image image = new Image(("/images/blueLove.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
        }else{
            System.out.println("user removed this movie from his favorites");
            Image image = new Image(("/images/unfilledLike.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(30);
        }
        DropShadow d = new DropShadow();
        d.setSpread(.7);
        likeImageView.setEffect(d);
    }

    public void mousePressedOnCancel(MouseEvent mouseEvent){
        System.out.println("user disliked this movie, remove it from his feed");
    }
}
