package movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class movieInfo {
    @FXML
    private ImageView cancelImageView;

    @FXML
    private ImageView likeImageView;
    private boolean clicked = false;
    @FXML
    void mousePressedOnAddToFavorites(MouseEvent event) {
        System.out.println("movie added to the favorites");
        clicked = !clicked;
        if(clicked == true) {
            System.out.println("movie added to the favorites");
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
            System.out.println("movie removed from his favorites");
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
    @FXML
    void mousePressedOnCancel(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }
}
