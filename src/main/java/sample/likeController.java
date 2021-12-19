package sample;
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
import movies.Movie;
import org.json.JSONObject;
import users.User;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

public class likeController {

    @FXML
    private Button backButton;
//
    public static void addLikedMovietoDB(Movie movieobj) throws Exception {
        //SQL injection
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection myConn = DriverManager.getConnection(url, "root", ""); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement();

        try{
            JSONObject jsonObject = movieobj.getJsonObject();
            int movieid = jsonObject.getInt("id");
            String username = "testing";
            String query="INSERT INTO `movie`(`MovieID`, `Username`) VALUES (?,?)";
            PreparedStatement preStat = myConn.prepareStatement(query);
            preStat.setInt(1,movieid);
            preStat.setString(2,username);
            preStat.executeUpdate();
            System.out.println("movie fav added to db");
            System.out.println(movieid);


//            movie.setJsonObject(jsonObject);


//                Stage stage1 = (Stage) backButton.getScene().getWindow();
//                stage1.close();
//                Parent root = FXMLLoader.load(getClass().getResource("loginController.fxml"));
//                Stage loginStage = new Stage();
//                loginStage.initStyle(StageStyle.DECORATED);
//                loginStage.setTitle("Login Page");
//                loginStage.setScene(new Scene(root, 530, 320));
//                loginStage.getIcons().add(new Image("/images/img.png"));
//                loginStage.show();
//
//                Stage stage = (Stage) backButton.getScene().getWindow();
//                stage.close();
//            }
//            else{
//                //Put error Label Here
//                System.out.println("Please fill all the data");
//            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
