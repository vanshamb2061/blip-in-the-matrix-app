package login;

import apiKeys.GlobalData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

//Class to select preferred genres after registration

public class SelectGenre implements Initializable {
    @FXML
    private CheckBox actionCheckBox;

    @FXML
    private CheckBox comedyCheckBox;

    @FXML
    private CheckBox dramaCheckBox;

    @FXML
    private CheckBox fantasyCheckBox;

    @FXML
    private CheckBox horrorCheckBox;

    @FXML
    private CheckBox mysteryCheckBox;

    @FXML
    private CheckBox romanceCheckBox;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox thrillerCheckBox;

    List<CheckBox> genreCheckBox = new ArrayList<CheckBox>();
    Map<String, Boolean> genreMap = new HashMap<String, Boolean>();
    Map<String,Integer> genreRating = new HashMap<String,Integer>();
    String username = GlobalData.getUserId();


    public void submitButtonOnAction(ActionEvent event) throws Exception {
        System.out.println("Username: " + username);
        //Method to enter selected genres in map
        for(CheckBox genre : genreCheckBox){
            if(genre.isSelected()){
                genreMap.put(genre.getText(), true);
            }else{
                genreMap.put(genre.getText(), false);
            }
            //System.out.println(genreMap.get(genre.getText()));
        }

        setGenreInRegistrationController();
        saveGenres(username);

//        Parent root = FXMLLoader.load(getClass().getResource("/fxmlFile/loginPage.fxml"));
//        Stage loginStage = new Stage();
//        loginStage.initStyle(StageStyle.DECORATED);
//        loginStage.setTitle("Login Page");
//        loginStage.setScene(new Scene(root, 530, 320));
//        loginStage.getIcons().add(new Image("/images/img.png"));
//        loginStage.show();

        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }

    public void setGenreInRegistrationController() throws Exception {
        System.out.println("Username: " + username);
        for (Map.Entry<String, Boolean> entry : genreMap.entrySet()) {
            if(entry.getValue())
                genreRating.put(entry.getKey(),15);
            else
                genreRating.put(entry.getKey(),0);
            System.out.println("Genre Key = " + entry.getKey() + ", Value = " + entry.getValue());
            System.out.println("Genre Status: " + entry.getKey() + entry.getValue() + " : " + genreRating.get(entry.getKey()));
        }
//        selectionCnt += 1;
    }

    public void saveGenres(String username) throws Exception {
        //Method to enter saved genres in DB
        System.out.println("Username: " + username);
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/watchlistproject";
        Connection myConn = DriverManager.getConnection(url, "root", ""); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement();
        String queryGenre="INSERT INTO `genre`(`Username`, `Action`, `Comedy`, `Drama`, `Fantasy`, `Horror`, `Mystery`, `Romance`, `Thriller`) VALUES (?,?,?,?,?,?,?,?,?)";
        System.out.println("query Written!");

        PreparedStatement preStatGenre = myConn.prepareStatement(queryGenre);
        System.out.println("preStatG written!");

        System.out.println(genreRating.get("Action"));

        preStatGenre.setString(1,username);
        preStatGenre.setInt(2,genreRating.get("Action"));
        preStatGenre.setInt(3,genreRating.get("Comedy"));
        preStatGenre.setInt(4,genreRating.get("Drama"));
        preStatGenre.setInt(5,genreRating.get("Fantasy"));
        preStatGenre.setInt(6,genreRating.get("Horror"));
        preStatGenre.setInt(7,genreRating.get("Mystery"));
        preStatGenre.setInt(8,genreRating.get("Romance"));
        preStatGenre.setInt(9,genreRating.get("Thriller"));
        System.out.println("Values entered");

        preStatGenre.executeUpdate();
        System.out.println("genre ratings added to db");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Username: " + username);
        submitButton.setDisable(true);
        final int[] cnt = {0};
        genreCheckBox.add(actionCheckBox);
        genreCheckBox.add(comedyCheckBox);
        genreCheckBox.add(dramaCheckBox);
        genreCheckBox.add(fantasyCheckBox);
        genreCheckBox.add(horrorCheckBox);
        genreCheckBox.add(mysteryCheckBox);
        genreCheckBox.add(romanceCheckBox);
        genreCheckBox.add(thrillerCheckBox);

        for (CheckBox language : genreCheckBox) {
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
                    if(cnt[0] > 2){
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
