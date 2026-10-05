package org.example.file.week05_assignment.controller;

import org.example.file.week05_assignment.dto.*;
import org.example.file.week05_assignment.service.MovieService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> create(@RequestBody MovieRequest request){
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
        return movieService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
