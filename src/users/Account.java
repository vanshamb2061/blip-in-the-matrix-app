package users;

import java.util.*;
import users.User;
import movies.Movie;

public class Account extends User {
    int age,gender;
    HashMap<String,Boolean> genres;
    HashMap<String,Boolean> languages;
    HashMap<String,Integer> likedGenres;
    Vector<Integer> likedMovies;
    Vector<Integer> dislikedMovies;



    public void addLike(Movie movieobj){
        likedMovies.add(movieobj.getId());
        likedGenres.put(movieobj.getGenre(),likedGenres.get(movieobj.getGenre())-5);
    }

    public void addDislike(Movie movieobj){
        dislikedMovies.add(movieobj.getId());
        likedGenres.put(movieobj.getGenre(),likedGenres.get(movieobj.getGenre())-5);
    }

}
