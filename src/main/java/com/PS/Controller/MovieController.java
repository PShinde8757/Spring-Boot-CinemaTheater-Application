package com.PS.Controller;


import com.PS.CustomException.MovieNotFoundException;
import com.PS.Model.Movies;
import com.PS.Repository.MoviesRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MovieController {

    private final MoviesRepository moviesRepository;

    public MovieController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping("/movie")
    public CollectionModel<EntityModel<Movies>> allMovies(){
        List<EntityModel<Movies>> entityModelList= moviesRepository.findAll()
                .stream()
                .map(movies -> EntityModel.of(movies,
                        linkTo(methodOn(MovieController.class).oneMovie(movies.getId())).withSelfRel(),
                        linkTo(methodOn(MovieController.class).allMovies()).withRel("All Movies")))
                .toList();
        return CollectionModel.of(entityModelList,
                linkTo(methodOn(MovieController.class).allMovies()).withSelfRel());
    }

    @GetMapping("/movie/{id}")
    public EntityModel<Movies> oneMovie(@PathVariable long id) {
        Movies movies = moviesRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        return EntityModel.of(movies,
                linkTo(methodOn(MovieController.class).oneMovie(id)).withSelfRel(),
                linkTo(methodOn(MovieController.class).allMovies()).withRel("All Movies"));
    }


}
