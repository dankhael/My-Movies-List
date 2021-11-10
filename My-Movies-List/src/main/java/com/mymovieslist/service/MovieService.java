package com.mymovieslist.service;

import com.mymovieslist.My.Movies.List.exception.MovieNotFoundException;
import com.mymovieslist.My.Movies.List.model.Movie;
import com.mymovieslist.My.Movies.List.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Movie updateMovie(Movie movie) {
        Movie foundMovie = this.findMovieById(movie.getId());
        foundMovie = movie;
        return movieRepo.save(foundMovie);
    }

    public void deleteMovie(Long id) {
        movieRepo.deleteMovieById(id);
    }

    public void addImdbMovies(String search) {
        Movie[] imdbMovies = imdbService.getMovies(search);
        for ( int i = 0; i< imdbMovies.length; i++) {
            Movie loopMovie = imdbMovies[i];
            if (this.existsMovieByTitleId(loopMovie.getTitleId())) {
                this.addMovie(loopMovie);
            } else {
                System.out.println("Movie Already Exists");
            }
        }
    }

    public Movie findMovieById(Long id){
        return movieRepo.findMovieById(id).orElseThrow(() -> new MovieNotFoundException("Movie by Id "+ id +" was not found"));
    }

    public List<Movie> findMoviesByName(String name){
        return movieRepo.findMoviesByNameContains(name);
    }

    public boolean existsMovieByTitleId(String titleId) {
        if (movieRepo.findMovieByTitleId(titleId).isEmpty()){
         return true;
        } else {
            return false;
        }
    }

}
