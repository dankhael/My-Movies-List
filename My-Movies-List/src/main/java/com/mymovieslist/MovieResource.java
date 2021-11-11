package com.mymovieslist;

import com.mymovieslist.model.Movie;
import com.mymovieslist.service.ImdbService;
import com.mymovieslist.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieResource {
    private final MovieService movieService;
    private final ImdbService imdbService;

    public MovieResource(MovieService movieService, ImdbService imdbService) {
        this.movieService = movieService;
        this.imdbService = imdbService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        Movie movie = movieService.findMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie newMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie, @PathVariable("id") Long id) {
        System.out.println(movie);
        System.out.println(id);
        Movie updateMovie = movieService.updateMovie(movie);
        return new ResponseEntity<>(updateMovie, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/imdb/{name}")
    public  ResponseEntity<?> getImdbMovies(@PathVariable("name") String name){
        System.out.println("STARTING IMDB REQUEST");
        movieService.addImdbMovies(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/find_name/{name}")
    public ResponseEntity<List<Movie>> getMoviesByName(@PathVariable("name") String name) {
        List<Movie> movies = movieService.findMoviesByName(name);
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
