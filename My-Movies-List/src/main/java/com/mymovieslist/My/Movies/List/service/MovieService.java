package com.mymovieslist.My.Movies.List.service;

import com.mymovieslist.My.Movies.List.exception.UserNotFoundException;
import com.mymovieslist.My.Movies.List.model.Movie;
import com.mymovieslist.My.Movies.List.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.*;

@Service
public class MovieService {
    private final MovieRepo movieRepo;
    private final ImdbService imdbService;

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

    public Movie updateMovie(Long id, Movie movie) {
        Movie foundMovie = this.findMovieById(id);
        foundMovie.setTitleId(movie.getTitleId());
        foundMovie.setFavorite(movie.getFavorite());
        foundMovie.setImageUrl(movie.getImageUrl());
        foundMovie.setName(movie.getName());
        foundMovie.setRating(movie.getRating());
        foundMovie.setLaunchDate(movie.getLaunchDate());
        return movieRepo.save(foundMovie);
    }

    public void deleteMovie(Long id) {
        movieRepo.deleteMovieById(id);
    }

    public void AddImdbMovies (String search) {
        Movie[] imdbMovies = imdbService.getMovies(search);
        for ( int i = 0; i< imdbMovies.length; i++) {
            Movie loopMovie = imdbMovies[i];
            if (this.findMovieByTitleId(loopMovie.getTitleId())) {
                this.addMovie(loopMovie);
            } else {
                System.out.println("Movie Already Exists");
            }
        }
    }

    public Movie findMovieById(Long id){
        return movieRepo.findMovieById(id).orElseThrow(() -> new UserNotFoundException("Movie by Id "+ id +" was not found"));
    }

    public List<Movie> findMoviesByName(String name){
        return movieRepo.findMoviesByNameContains(name);
    }

    public boolean findMovieByTitleId(String titleId) {
        if (movieRepo.findMovieByTitleId(titleId).isEmpty()){
         return true;
        } else {
            return false;
        }
    }

}
