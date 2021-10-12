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
import java.sql.PreparedStatement;
import java.sql.Statement;

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
        // Password and Confirm Password are same or not I haven't checked it

        //sql injection
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/userinfo";
        Connection myConn = DriverManager.getConnection(url, "root", ""); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement();

        try
        {
            String query="INSERT INTO `userdetails`(`First Name`, `Last Name`, `Age`, `UserId`, `Password`) VALUES (?,?,?,?,?)";
            PreparedStatement preStat = myConn.prepareStatement(query);
            preStat.setString(1,firstNameTextField.getText());
            preStat.setString(2,lastNameTextField.getText());
            preStat.setString(3,ageTextField.getText());
            preStat.setString(4,usernameTextField.getText());
            preStat.setString(5,setPasswordField.getText());
            preStat.executeUpdate();

            System.out.println("Registered Successfully");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

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
