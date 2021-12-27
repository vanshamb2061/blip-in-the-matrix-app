package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.LikeController;

//Class to handle mouseevents on newmovies

public class NewMoviesController {
    @FXML
    private Label genreLabel;

    @FXML
    private ImageView poster;

    @FXML
    private Label titleLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private ImageView likeImageView;

    private boolean clicked = false;
    private Movie tryNewMovie;

    public void setData(Movie tryNewMovie){
        //Method to set data for new movie
        this.tryNewMovie = tryNewMovie;
        titleLabel.setText(tryNewMovie.getName());
        genreLabel.setText(tryNewMovie.getGenre());
        yearLabel.setText(tryNewMovie.getYear());
        Image image = new Image(tryNewMovie.getImgSrc());
        poster.setImage(image);
    }

    public void mousePressedOnPoster(MouseEvent mouseEvent) throws Exception{
        //Method to handle mouseevent when user clicks on poster
        System.out.println("user clicked on movie, show him movie description");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/movieInfo.fxml"));
        Parent root = loader.load();
        MovieInfo movieInfoController = loader.getController();
        movieInfoController.setEverythingInMovieInfo(tryNewMovie);
        Stage movieInfoStage = new Stage();
        movieInfoStage.initStyle(StageStyle.UNDECORATED);
        movieInfoStage.setTitle("Info");
        movieInfoStage.setScene(new Scene(root, 930, 545));
        movieInfoStage.getIcons().add(new Image("/images/img.png"));
        movieInfoStage.initModality(Modality.APPLICATION_MODAL);
        movieInfoStage.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow() );
        movieInfoStage.show();
    }

    public void mousePressedOnLike(MouseEvent mouseEvent) throws Exception {
        //Method to handle mouse event when user clicks on like
        clicked = !clicked;
        if(clicked == true) {
            System.out.println("user liked this movie, added this to his favorites");
            Image image = new Image(("/images/blueLove.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(24);

            LikeController likeController = new LikeController();
            likeController.addLikedMovietoDB(tryNewMovie);
        }else{
            System.out.println("user removed this movie from his favorites");
            Image image = new Image(("/images/unfilledLike.png"));
            likeImageView.setImage(image);
            likeImageView.setFitWidth(29);
            likeImageView.setFitHeight(24);
        }
        DropShadow d = new DropShadow();
        d.setSpread(.7);
        likeImageView.setEffect(d);
    }

    public void mousePressedOnCancel(MouseEvent mouseEvent) {
        System.out.println("user disliked this movie, remove it from his feed");
    }

}
