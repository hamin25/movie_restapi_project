package org.example.file.week05_assignment.service;

import org.example.file.week05_assignment.domain.Movie;
import org.example.file.week05_assignment.dto.*;
import org.example.file.week05_assignment.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;


@Service
public class MovieService {
    private final MovieRepository repository;
    public MovieService(MovieRepository repository){
        this.repository = repository;
    }
    public MovieResponse create(MovieRequest r){
        return toResponse(repository.save(new Movie(null,r.title(),r.director(),r.genre(),r.runningTime(),r.score())));
    }
    public List<MovieResponse> findAll(){
        return repository.findAll().stream().map(this::toResponse).toList();

    }

    public MovieResponse findById(Long id){
        return toResponse(findMovie(id));
    }

    public MovieResponse update(Long id, MovieRequest r){
        Movie m = findMovie(id);
        m.setTitle(r.title());
        m.setDirector(r.director());
        m.setGenre(r.genre());
        m.setRunningTime(r.runningTime());
        m.setScore(r.score());
        return toResponse(repository.update(m));
    }
    public void delete(Long id){
        findMovie(id); // 404오류를 내기 위한 목적으로
        repository.deleteById(id);
    }



    private Movie findMovie(Long id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie not founded: " +id));
    }

    private MovieResponse toResponse(Movie r){
        return new MovieResponse(r.getId(),r.getTitle(),r.getDirector(),r.getGenre(),r.getRunningTime(),r.getScore());
    }
}
