package movies;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javafx.scene.web.*;

public class playTrailer implements Initializable {

    @FXML
    private Button backwardButton;
    @FXML
    private Button forwardButton;
    @FXML
    private WebView webView;
    @FXML
    private MediaView mediaView;
    @FXML
    private ToggleButton playPauseButton;
    @FXML
    private ImageView cancelImageView;
    @FXML
    private HBox hbox;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File file;

    @FXML
    void backwardButtonOnAction(ActionEvent event) {

    }
    @FXML
    void forwardButtonOnAction(ActionEvent event) {

    }
    @FXML
    void playPauseButtonOnAction(ActionEvent event) {
        mediaPlayer.play();
    }
    @FXML
    void mousePressedOnCancelImageView(MouseEvent event) {
        Stage stage = (Stage) cancelImageView.getScene().getWindow();
        stage.close();
    }

    public void playingTrailer(String video) throws JSONException {

        System.out.println("hellow guys");
        media = new Media("https://www.youtube.com/watch?v=6JnN1DmbqoU");
        mediaView = new MediaView();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setVisible(true);

        hbox.getChildren().add(mediaView);

    }
    public void play() throws Exception{
        /*
        HttpURLConnection connection = null;
        final String mykey = "201d9cf62a43a21c17cdf0f13ce41312";
        String genres = "Action";
        boolean adult = true;
        int Current_Pg = 1;

        URL url = new URL("https://api.themoviedb.org/3/movie/297762/videos?api_key=" + mykey + "&language=en-US"
                + "&include_adult=" + adult + "&page="+Current_Pg);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder response = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            response.append(line);
            response.append("\r");
        }
        reader.close();
        String result = response.toString();

        JSONObject jsonObject1 = new JSONObject(result);
        JSONArray jsonArray = jsonObject1.getJSONArray("results");

        JSONObject jsonObject = jsonArray.getJSONObject(1);

        System.out.println("https://www.youtube.com/watch?v=" + jsonObject.getString("key"));
        System.out.println("starting to play media");
        //mediaPlayer.setAutoPlay(true);
        //String link = "https://api.themoviedb.org/3/movie/297762/videos?api_key=201d9cf62a43a21c17cdf0f13ce41312&language=en-US"
        media = new Media("https://www.youtube.com/watch?v=" + jsonObject.getString("key"));

        MediaView mediaView = new MediaView();
        media = new Media("https://www.youtube.com/watch?v=VSB4wGIdDwo");
        System.out.println("https://www.youtube.com/watch?v=VSB4wGIdDwo");
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
        //setting group and scene
        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,500,400);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Playing video");
        primaryStage.show();

       */


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            play();
        } catch (Exception e) {
            e.printStackTrace();
        }
   /*     webView = new WebView();
        webView.getEngine().load(
                "http://www.youtube.com/embed/utUPth77L_o?autoplay=1"
        );
*/

        WebView webview = new WebView();
        webview.setPrefSize(640, 390);
        webview.getEngine().load("https://www.youtube.com/watch?v=UT5F9AXjwhg");

        Stage stage = new Stage();
        stage.setScene(new Scene(webview));
        stage.show();


 /*     Media media = new Media("https://bit.ly/3jbT6qI");
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.play();

        String path = "C:\\0programming\\0PROJECT\\WATCHLIST\\blip-in-the-matrix-app\\src\\movies\\avengers.mp4";

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //Instantiating MediaView class
        MediaView mediaView = new MediaView(mediaPlayer);

        //by setting this property to true, the Video will be played
        mediaPlayer.setAutoPlay(true);

        //setting group and scene
        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene scene = new Scene(root,500,400);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Playing video");
        primaryStage.show();
        */
    }



}