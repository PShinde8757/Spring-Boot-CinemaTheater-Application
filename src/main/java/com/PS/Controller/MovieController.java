package com.PS.Controller;


import com.PS.Assembler.MovieAssembler;
import com.PS.CustomException.MovieNotFoundException;
import com.PS.Model.Movies;
import com.PS.Model.Status;
import com.PS.Repository.MoviesRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MovieController {

    private final MoviesRepository moviesRepository;
    private final MovieAssembler movieAssembler;

    public MovieController(MoviesRepository moviesRepository, MovieAssembler movieAssembler) {
        this.moviesRepository = moviesRepository;
        this.movieAssembler = movieAssembler;
    }

    @GetMapping("/movie")
    public CollectionModel<EntityModel<Movies>> allMovies(){
        List<EntityModel<Movies>> entityModelList= moviesRepository.findAll()
                .stream()
                .map(movieAssembler::toModel)
                        /*(movies -> EntityModel.of(movies,
                            linkTo(methodOn(MovieController.class).oneMovie(movies.getId())).withSelfRel(),
                            linkTo(methodOn(MovieController.class).allMovies()).withRel("All Movies")))*/
                .toList();
        return CollectionModel.of(entityModelList,
                linkTo(methodOn(MovieController.class).allMovies()).withSelfRel());
    }

    @GetMapping("/movie/{id}")
    public EntityModel<Movies> oneMovie(@PathVariable long id) {
        Movies movies = moviesRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        return movieAssembler.toModel(movies);
                /*EntityModel.of(movies,
                    linkTo(methodOn(MovieController.class).oneMovie(id)).withSelfRel(),
                    linkTo(methodOn(MovieController.class).allMovies()).withRel("All Movies"));*/
    }

    @PostMapping("/movie")
    public ResponseEntity<?>addMovie(@RequestBody Movies newMovie){
        EntityModel<Movies>addMovie=movieAssembler.toModel(moviesRepository.save(newMovie));
        return ResponseEntity
                .created(addMovie.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(addMovie);
    }

    @PutMapping("/movie/{id}/complete")
    ResponseEntity<?> complete(@PathVariable Long id){
        Movies movies= moviesRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
        if (movies.getStatus()== Status.IN_PROGRESS){
            movies.setStatus(Status.COMPLETE);
            return ResponseEntity.ok(movieAssembler.toModel(moviesRepository.save(movies)));
        }
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the " + movies.getStatus() + " status"));
    }




}
