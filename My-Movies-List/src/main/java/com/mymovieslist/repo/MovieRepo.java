package com.mymovieslist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mymovieslist.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Long> {
    void deleteMovieById(Long id);

    Optional<Movie> findMovieById(Long id);

    Optional<Movie> findMovieByTitleId(String titleId);

    Optional<List<Movie>> findMoviesByNameContains(String name);

    Optional<List<Movie>> findMoviesByFavorite(Boolean favorite);
}
