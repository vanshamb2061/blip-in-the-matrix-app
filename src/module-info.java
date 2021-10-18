module WATCHLIST {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    //requires mysql.connector.java;
    opens sample;
    opens movies;
    opens images;

}