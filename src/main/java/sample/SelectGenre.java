package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SelectGenre implements Initializable {
    @FXML
    private CheckBox actionCheckBox;

    @FXML
    private CheckBox comedyCheckBox;

    @FXML
    private CheckBox dramaCheckBox;

    @FXML
    private CheckBox fantasyCheckBox;

    @FXML
    private CheckBox horrorCheckBox;

    @FXML
    private CheckBox mysteryCheckBox;

    @FXML
    private CheckBox romanceCheckBox;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox thrillerCheckBox;

    List<CheckBox> genreCheckBox = new ArrayList<CheckBox>();
    Map<String, Boolean> genreMap = new HashMap<String, Boolean>();


    public void submitButtonOnAction(ActionEvent event) throws IOException {
        // code to write
        //sql injection to store data

        for(CheckBox genre : genreCheckBox){
            if(genre.isSelected()){
                genreMap.put(genre.getText(), true);
            }else{
                genreMap.put(genre.getText(), false);
            }
            //System.out.println(genreMap.get(genre.getText()));
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/registrationPage.fxml"));
        Parent root = loader.load();
        RegistrationController registrationController = loader.getController();
        registrationController.setGenreInRegistrationController(genreMap);

        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setDisable(true);
        final int[] cnt = {0};
        genreCheckBox.add(actionCheckBox);
        genreCheckBox.add(comedyCheckBox);
        genreCheckBox.add(dramaCheckBox);
        genreCheckBox.add(fantasyCheckBox);
        genreCheckBox.add(horrorCheckBox);
        genreCheckBox.add(mysteryCheckBox);
        genreCheckBox.add(romanceCheckBox);
        genreCheckBox.add(thrillerCheckBox);

        for (CheckBox language : genreCheckBox) {
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    if (language.isSelected()) {
                        System.out.println(language.getText() + " is Selected");
                        cnt[0]++;
                    }else{
                        System.out.println(language.getText() + " is not Selected");
                        cnt[0]--;
                    }
                    if(cnt[0] > 2){
                        submitButton.setDisable(false);
                    }else{
                        submitButton.setDisable(true);
                    }
                }
            };
            language.setOnAction(event);
        }
        System.out.println(cnt[0]);
    }

}
