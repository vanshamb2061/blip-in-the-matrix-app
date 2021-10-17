package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class selectGenre implements Initializable {
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

    List<CheckBox> languageCheckBox = new ArrayList<CheckBox>();

    public void submitButtonOnAction(ActionEvent event){
        // code to write
        //sql injection to store data

        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setDisable(true);
        final int[] cnt = {0};
        languageCheckBox.add(actionCheckBox);
        languageCheckBox.add(comedyCheckBox);
        languageCheckBox.add(dramaCheckBox);
        languageCheckBox.add(fantasyCheckBox);
        languageCheckBox.add(horrorCheckBox);
        languageCheckBox.add(mysteryCheckBox);
        languageCheckBox.add(romanceCheckBox);
        languageCheckBox.add(thrillerCheckBox);

        for (CheckBox language : languageCheckBox) {
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
