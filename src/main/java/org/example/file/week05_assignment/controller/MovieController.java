package org.example.file.week05_assignment.controller;

import org.example.file.week05_assignment.dto.*;
import org.example.file.week05_assignment.service.MovieService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    private void validate(MovieRequest r){
        if(r.title()=="") throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"제목은 필수 입력");
        if(r.director()=="") throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"감독은 필수 입력");
        if(r.score()<1 || r.score()>5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"평점은 1~5점 사이");
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request){
        validate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(request));
    }

    @GetMapping
    public List<MovieResponse> findAll(){
        return movieService.findAll();
    }

    @GetMapping("/{id}")
    public MovieResponse findById(@PathVariable Long id){
        return movieService.findById(id);
    }

    @PutMapping("/{id}")
    public MovieResponse update(@PathVariable Long id, @RequestBody MovieRequest request){
        validate(request);
        return movieService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
