package movies;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class newMoviesController {
    @FXML
    private Label genreLabel;

    @FXML
    private ImageView poster;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    private newMovie tryNewMovie;

    public void setData(newMovie tryNewMovie){
        this.tryNewMovie = tryNewMovie;
        titleLabel.setText(tryNewMovie.getName());
        genreLabel.setText(tryNewMovie.getGenre());
        yearLabel.setText(tryNewMovie.getYear());
        Image image = new Image(getClass().getResourceAsStream(tryNewMovie.getImgSrc()));
        poster.setImage(image);
    }

}
