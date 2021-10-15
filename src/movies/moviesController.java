package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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


}
