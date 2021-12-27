package login;

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

public class SelectLanguage implements Initializable {

    Map<String, Boolean> languageMap = new HashMap<String, Boolean>();

    @FXML
    private CheckBox englishCheckBox;
    @FXML
    private CheckBox hindiCheckBox;
    @FXML
    private Button submitButton;
    @FXML
    private CheckBox tamilCheckBox;

    List<CheckBox> languageCheckBox = new ArrayList<CheckBox>();

    public void submitButtonOnAction(ActionEvent event) throws IOException {
        // code to write

        for(CheckBox language : languageCheckBox){
            if(language.isSelected()){
                languageMap.put(language.getText(), true);
            }else{
                languageMap.put(language.getText(), false);
            }
            //System.out.println(languageMap.get(language.getText()));
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/registrationPage.fxml"));
        Parent root = loader.load();
        RegistrationController registrationController = loader.getController();
        registrationController.setLanguageInRegistrationController(languageMap);

        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submitButton.setDisable(true);
        final int[] cnt = {0};
        languageCheckBox.add(hindiCheckBox);
        languageCheckBox.add(englishCheckBox);
        languageCheckBox.add(tamilCheckBox);
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
                    if(cnt[0] > 0){
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
