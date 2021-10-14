package sample;

import java.io.*;
import java.util.*;

public class Account extends User{
    int age,gender;
    HashMap<String,Boolean> genres;
    HashMap<String,Boolean> languages;
    HashMap<String,Integer> likedGenres;
    Vector<String> likedMovies;
    Vector<String> dislikedMovies;
    public void addLike(Movie movieobj) {
        likedMovies.add(movieobj.name);
        likedGenres.put(movieobj.genre,likedGenres.get(movieobj.genre)+10);
    }
    public void addDislike(Movie movieobj){
        dislikedMovies.add(movieobj.name);
        likedGenres.put(movieobj.genre,likedGenres.get(movieobj.genre)-5);
    }

}
