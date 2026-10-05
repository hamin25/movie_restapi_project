package org.example.file.week05_assignment.repository;

import org.example.file.week05_assignment.domain.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Movie save(Movie movie);
    List<Movie> findAll();
    Optional<Movie> findById(Long id);
    Movie update(Movie movie);
    void deleteById(Long id);
    List<Movie> findByScore(Long score);
}
