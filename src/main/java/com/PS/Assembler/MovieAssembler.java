package com.PS.Assembler;

import com.PS.Controller.MovieController;
import com.PS.Model.Movies;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class MovieAssembler implements RepresentationModelAssembler<Movies, EntityModel<Movies>> {

    @Override
    public EntityModel<Movies> toModel(Movies entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(MovieController.class).oneMovie(entity.getId())).withSelfRel(),
                linkTo(methodOn(MovieController.class).allMovies()).withRel("All Movies"));
    }
}
