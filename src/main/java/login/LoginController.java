package login;

import apiKeys.GlobalData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import sample.DashboardController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//Method to handle login

public class LoginController {

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

    public void clickOnLogin() throws IOException {
        //Method to check if the login details are entered correctly
        if(!usernameTextField.getText().isEmpty() && !enterPasswordField.getText().isEmpty()){
            //sql injection
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/watchlistproject";
                Connection connection = DriverManager.getConnection(url, "root", "");

                String username = usernameTextField.getText();
                String password = enterPasswordField.getText();

                Statement stm = connection.createStatement();

                String sql = "select * from user where username='" + username + "' and Password='" + password + "'";
                ResultSet result = stm.executeQuery(sql);

                if (result.next()) {
                    GlobalData.setUserId(username);
                    System.out.println(GlobalData.getUserId());
                    System.out.println(result.getInt("Age"));
                    GlobalData.setUserAge(result.getInt("Age"));
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlFile/dashboardController.fxml"));
                    Parent root = loader.load();
                    DashboardController dashboardController = loader.getController();
                    dashboardController.setUserNameInDashboardController(usernameTextField.getText());
                    Stage dashboardStage = new Stage();
                    dashboardStage.initStyle(StageStyle.DECORATED);
                    dashboardStage.setTitle("watchlist");
                    dashboardStage.setScene(new Scene(root, 1090, 790));
                    dashboardStage.getIcons().add(new Image("/images/img.png"));
                    dashboardStage.setMinHeight(600);
                    dashboardStage.setMinWidth(985);
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
        //Method to load registration page
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/registrationPage.fxml"));
        Stage registrationStage = new Stage();
        registrationStage.initStyle(StageStyle.UNDECORATED);
        registrationStage.setScene(new Scene(root, 540, 550));
        registrationStage.getIcons().add(new Image("/images/img.png"));
        registrationStage.show();
    }
}
