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

public class loginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private TextField usernameTextField;

    public void loginButtonOnAction(ActionEvent event) throws Exception {
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            //sql injection
        }else{
            errorLabel.setText("please enter username/password");
        }
    }

    public void signupButtonOnAction(ActionEvent event) throws Exception{
        createRegistrationPage();
    }
    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void createRegistrationPage() throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("registrationPage.fxml"));
        Stage registrationStage = new Stage();
        registrationStage.initStyle(StageStyle.UNDECORATED);
        registrationStage.setScene(new Scene(root, 540, 550));
        registrationStage.show();
    }
}
