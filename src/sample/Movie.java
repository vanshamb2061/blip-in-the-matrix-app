package sample;

import java.util.*;

public class Movie {
    int likes,dislikes;
    String name;
    String genre;
    Movie(){
        dislikes=0;
        likes=0;
    }
    public String getName(){
        return name;
    }
    public String getGenre(){
        return genre;
    }
    public int getLikes(){
        return likes;
    }
    public void addLike(){
        likes=likes+1;
    }
    public void addDisLike(){
        dislikes=dislikes+1;
    }
}
