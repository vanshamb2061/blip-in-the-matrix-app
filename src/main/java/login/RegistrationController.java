package login;
import apiKeys.GlobalData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

//Class to handle registrations

public class RegistrationController {
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
    int selectionCnt = 0;
    public void registerButtonOnAction(ActionEvent event) throws Exception {
        //Method to handle registration
        String username = usernameTextField.getText();
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection myConn = DriverManager.getConnection(url, "root", ""); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement();

        try{
            String query="INSERT INTO `user`(`First Name`, `Last Name`, `Age`, `Username`, `Password`) VALUES (?,?,?,?,?)";
            PreparedStatement preStat = myConn.prepareStatement(query);
            preStat.setString(1,firstNameTextField.getText());
            preStat.setString(2,lastNameTextField.getText());
            preStat.setString(3,ageTextField.getText());
            preStat.setString(4,usernameTextField.getText());
            preStat.setString(5,setPasswordField.getText());
            if(!firstNameTextField.getText().isEmpty()
                    && !ageTextField.getText().isEmpty()
                    && !usernameTextField.getText().isEmpty()
                    && !setPasswordField.getText().isEmpty()
                    && !confirmPasswordField.getText().isEmpty()
//                    && selectionCnt > 1
                    && setPasswordField.getText().equals(setPasswordField.getText())) {
                preStat.executeUpdate();
                System.out.println("Query executed!");
                GlobalData.setUserId(username);

                Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/selectGenre.fxml"));
                System.out.println("FXML file loaded!");

                Stage genreStage = new Stage();
                genreStage.initStyle(StageStyle.DECORATED);
                genreStage.setTitle("Genre Page");
                genreStage.setScene(new Scene(root, 280, 400));
                genreStage.getIcons().add(new Image("/images/img.png"));
                genreStage.show();


                Stage stage1 = (Stage) registerButton.getScene().getWindow();
                stage1.close();

            }
            else{
                //Put error Label Here
                System.out.println("Please fill all the data");
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }



    public void selectLanguageButtonOnAction(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/selectLanguage.fxml"));
        Stage selectLanguageStage = new Stage();
//        selectLanguageStage.initStyle(StageStyle.UNDECORATED);
        selectLanguageStage.setScene(new Scene(root, 230, 230));
        selectLanguageStage.getIcons().add(new Image("/images/img.png"));
        selectLanguageStage.show();
    }
    public void closeButtonOnAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public Map<String,Integer> setGenreInRegistrationController(Map<String, Boolean> genreMap) throws Exception {
        Map<String,Integer> genreRating = new HashMap<String, Integer>();;
        for (Map.Entry<String, Boolean> entry : genreMap.entrySet()) {
            if(entry.getValue())
                genreRating.put(entry.getKey(),15);
            else
                genreRating.put(entry.getKey(),0);
            System.out.println("Genre Key = " + entry.getKey() + ", Value = " + entry.getValue());
            System.out.println("Genre Status: " + entry.getKey() + entry.getValue() + " : " + genreRating.get(entry.getKey()));
        }
        selectionCnt += 1;
        return genreRating;
    }

    public void setLanguageInRegistrationController(Map<String, Boolean> languageMap) {
        for (Map.Entry<String, Boolean> entry : languageMap.entrySet()) {
            System.out.println("Lang Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        selectionCnt += 1;
    }
}
