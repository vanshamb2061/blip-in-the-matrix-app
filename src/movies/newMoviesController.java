package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

    public void mousePressedOnPoster(MouseEvent mouseEvent) throws Exception{
        System.out.println("user clicked on movie, show him movie description");
        Parent root = FXMLLoader.load(getClass().getResource("movieInfo.fxml"));
        Stage movieInfoStage = new Stage();
        movieInfoStage.initStyle(StageStyle.DECORATED);
        movieInfoStage.setTitle("Info");
        movieInfoStage.setScene(new Scene(root, 530, 320));
        movieInfoStage.show();
    }

    public void mousePressedOnLike(MouseEvent mouseEvent) {
        System.out.println("user liked this movie, added this to his favorites");
    }

    public void mousePressedOnCancel(MouseEvent mouseEvent) {
        System.out.println("user disliked this movie, remove it from his feed");
    }

}
