package sample;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

public class registrationController {
    @FXML
    private Button registerButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button selectGenreButton;
    @FXML
    private Button selectLanguageButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField ageTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;

    public void registerButtonOnAction(ActionEvent event) throws Exception {
        //write backend code
        //sql injection
    }
    public void selectGenreButtonOnAction(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("selectGenre.fxml"));
        Stage selectGenreStage = new Stage();
        selectGenreStage.initStyle(StageStyle.UNDECORATED);
        selectGenreStage.setScene(new Scene(root, 280, 400));
        selectGenreStage.show();
    }

    public void selectLanguageButtonOnAction(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("selectLanguage.fxml"));
        Stage selectLanguageStage = new Stage();
        selectLanguageStage.initStyle(StageStyle.UNDECORATED);
        selectLanguageStage.setScene(new Scene(root, 230, 230));
        selectLanguageStage.show();
    }
    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
