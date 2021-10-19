package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public void clickOnLogin(){
        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            //sql injection
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/userinfo";
                Connection connection = DriverManager.getConnection(url, "root", "");

                String username = usernameTextField.getText();
                String password = enterPasswordField.getText();

                Statement stm = connection.createStatement();

                String sql = "select * from userdetails where UserId='" + username + "' and Password='" + password + "'";
                ResultSet result = stm.executeQuery(sql);

                if (result.next()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboardController.fxml"));
                    Parent root = loader.load();
                    dashboardController dashboardController = loader.getController();
                    dashboardController.setUserNameInDashboardController(usernameTextField.getText());
                    Stage dashboardStage = new Stage();
                    dashboardStage.initStyle(StageStyle.DECORATED);
                    dashboardStage.setTitle("watchlist");
                    dashboardStage.setScene(new Scene(root, 900, 700));
                    dashboardStage.getIcons().add(new Image("/images/img.png"));
                    dashboardStage.show();

                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();
                } else {
                    //If username don't exist
                    errorLabel.setText("Entered username/password is wrong");
                    usernameTextField.setText("");
                    enterPasswordField.setText("");
                }
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }

        }else{
            errorLabel.setText("please enter username/password");
        }
    }
    public void keyPressedOnPasswordField(KeyEvent event) throws Exception{
        if(event.getCode().equals(KeyCode.ENTER)){
            System.out.println("login button pressed");
            clickOnLogin();
        }
    }
    public void loginButtonOnAction(ActionEvent event) throws Exception {
        clickOnLogin();
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
        registrationStage.getIcons().add(new Image("/images/img.png"));
        registrationStage.show();
    }
}
