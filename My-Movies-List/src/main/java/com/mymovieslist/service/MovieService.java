package com.mymovieslist.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mymovieslist.exception.MovieNotFoundException;
import com.mymovieslist.model.ImdbMovie;
import com.mymovieslist.model.Movie;
import com.mymovieslist.repo.MovieRepo;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.*;

@Service
public class MovieService {
    private final MovieRepo movieRepo;
    private final ImdbService imdbService;
    private final Gson gson = new Gson();

    @Autowired
    public MovieService(MovieRepo movieRepo, ImdbService imdbService) {
        this.movieRepo = movieRepo;
        this.imdbService = imdbService;
    }

    public Movie addMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    public List<Movie> findAllMovies() {
        return movieRepo.findAll();
    }

    public Movie updateMovie(Movie movie) {
        Movie foundMovie = this.findMovieById(movie.getId());
        foundMovie = movie;
        return movieRepo.save(foundMovie);
    }

    public void deleteMovie(Long id) {
        movieRepo.deleteMovieById(id);
    }

    public void addImdbMovies(String search) {
        ArrayList<ImdbMovie> imdbMovies = imdbService.getMovies(search);
        for (ImdbMovie imdbMovie : imdbMovies) {
            Movie movie = parseMovie(imdbMovie.getSearchJson(), imdbMovie.getRatingsJson());
            if (this.existsMovieByTitleId(movie.getTitleId())){
                System.out.println("Movie already on Database");
            } else{
            this.addMovie(movie);
            }
        }
    }

    public Movie findMovieById(Long id){
        return movieRepo.findMovieById(id).orElseThrow(() -> new MovieNotFoundException("Movie by Id "+ id +" was not found"));
    }

    public List<Movie> findMoviesByName(String name){
        return movieRepo.findMoviesByNameContains(name).orElseThrow(() -> new MovieNotFoundException("Movies by Name"+ name + "were not found"));
    }

    public boolean existsMovieByTitleId(String titleId) {
        if (movieRepo.findMovieByTitleId(titleId).isEmpty()){
         return true;
        } else {
            return false;
        }
    }


    public Movie parseMovie(JsonObject searchJson, String ratingString) {
        JsonObject ratingJson = gson.fromJson(ratingString, JsonObject.class);

        // Get values from the first searchJson
        String titleId = searchJson.get("id").getAsString();
        String name = searchJson.get("name").getAsString();
        String imageUrl = searchJson.get("image").getAsString();
        int launchDate;
        Double rating;

        // Get values from the ratingJson
        try{
            launchDate = ratingJson.get("year").getAsInt();
        } catch( IllegalStateException e) {
            launchDate = 0;
            System.out.println("Launch date not found");
        }
        try{
            rating = ratingJson.get("imDb").getAsDouble();
        } catch( IllegalStateException e) {
            rating = 0.0;
            System.out.println("Rating not found");
        }
        Movie movie = new Movie(titleId, name, launchDate, rating, imageUrl);
        return movie;

    }
}
