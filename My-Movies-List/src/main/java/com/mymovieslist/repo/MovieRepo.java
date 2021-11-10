package com.mymovieslist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mymovieslist.My.Movies.List.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    void deleteMovieById(Long id);

    Optional<Movie> findMovieById(Long id);
    Optional<Movie> findMovieByTitleId(String titleId);
    List<Movie> findMoviesByNameContains(String name);
}
