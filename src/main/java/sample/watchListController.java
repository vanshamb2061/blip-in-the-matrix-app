package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class watchListController {
    @FXML
    private Button logOutButton;
    @FXML
    private Button backButton;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Label welcomeUserLabel;
    @FXML
    private GridPane mainGridPane;

    public void mousePressedOnWatchList(MouseEvent mouseEvent) throws IOException {
        System.out.println("here you'll find all your saved watchlist");
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/watchListPage.fxml"));
        Stage watchListStage = new Stage();
        watchListStage.setScene(new Scene(root));
        watchListStage.show();
    }

    public void backButtonOnAction(ActionEvent e){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

}
