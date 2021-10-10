package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class selectGenre {
    @FXML
    private Button okButton;

    public void okButtonOnAction(ActionEvent event){
        // code to write

        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
