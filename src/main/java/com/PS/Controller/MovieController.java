package com.PS.Controller;


import com.PS.Model.Movies;
import com.PS.Repository.MoviesRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    private final MoviesRepository moviesRepository;

    public MovieController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping("/movie")
    List<Movies> allMovies(){
        return moviesRepository.findAll();
    }


}
