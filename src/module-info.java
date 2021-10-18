module WATCHLIST {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires mysql.connector.java;
    requires javafx.media;
    opens sample;
    opens movies;
    opens images;
    opens users;

}