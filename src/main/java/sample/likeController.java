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
    private Label welcomeUserLabel;
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
            String username = "ankit";
            String query="INSERT INTO `movie`(`MovieID`, `Username`) VALUES (?,?)";
            PreparedStatement preStat = myConn.prepareStatement(query);
            preStat.setInt(1,movieid);
            preStat.setString(2,username);
            preStat.executeUpdate();
            System.out.println("movie fav added to db");
            System.out.println(movieid);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

}
