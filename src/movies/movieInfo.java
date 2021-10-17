package movies;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class movieInfo {
    @FXML
    private ImageView cancelImageView;

    @FXML
    void mousePressedOnAddToFavorites(MouseEvent event) {
        System.out.println("movie added to the favorites");
    }

    @FXML
    void mousePressedOnPlayTrailer(MouseEvent event) {
        System.out.println("Please wait..... we're playing trailer for you.");
    }
    @FXML
    void mousePressedOnCancel(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }
}
