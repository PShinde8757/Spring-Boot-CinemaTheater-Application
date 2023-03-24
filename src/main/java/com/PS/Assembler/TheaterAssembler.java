package com.PS.Assembler;



import com.PS.Controller.TheaterController;
import com.PS.Model.Theater;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TheaterAssembler implements RepresentationModelAssembler<Theater , EntityModel<Theater> >{

    @Override
    public EntityModel<Theater> toModel(Theater entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(TheaterController.class).oneShow(entity.getId())).withSelfRel(),
                linkTo(methodOn(TheaterController.class).allShow()).withRel("All Shows "));
    }
}
