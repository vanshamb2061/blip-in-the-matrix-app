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

    public void loginButtonOnAction(ActionEvent event) throws Exception {
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
                    String FirstName = result.getString("First Name");
                    String LastName = result.getString("Last Name");
                    int Age = result.getInt("Age");

                    System.out.println("Login Accessed");
                    System.out.println("Name - " + FirstName);
                    System.out.println("Branch - " + LastName);
                    System.out.println("Age - " + Age);
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
