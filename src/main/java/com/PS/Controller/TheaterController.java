package com.PS.Controller;


import com.PS.CustomException.ShowNotFoundException;
import com.PS.Model.Theater;
import com.PS.Repository.TheaterRepository;
import com.PS.Assembler.TheaterAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TheaterController {

    private final TheaterRepository theaterRepository;
    private final TheaterAssembler theaterAssembler;

    public TheaterController(TheaterRepository theaterRepository, TheaterAssembler theaterAssembler) {
        this.theaterRepository = theaterRepository;
        this.theaterAssembler = theaterAssembler;
    }

    @GetMapping("/theater")
    public CollectionModel<EntityModel<Theater>> allShow(){

        List<EntityModel<Theater>> entityModels = theaterRepository.findAll()
                .stream()
                /*.map(theater ->EntityModel.of(theater,
                        linkTo(methodOn(TheaterController.class).oneShow(theater.getId())).withSelfRel(),
                        linkTo(methodOn(TheaterController.class).allShow()).withRel("All Shows ")))
                .collect(Collectors.toList());*/
                .map(theaterAssembler::toModel)
                .toList();
        return CollectionModel.of(entityModels,
                linkTo(methodOn(TheaterController.class).allShow()).withSelfRel());
    }

    @GetMapping("/theater/{id}")
    public EntityModel<Theater> oneShow(@PathVariable Long id){
        Theater theater =theaterRepository.findById(id)
                .orElseThrow(() -> new ShowNotFoundException(id));
        return theaterAssembler.toModel(theater);

    }

    @PostMapping("/theater")
    ResponseEntity<?> newShow(@RequestBody Theater newShow){
        EntityModel<Theater>addShow=theaterAssembler.toModel(theaterRepository.save(newShow));
        return ResponseEntity
                .created(addShow.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(addShow);
    }

    @PutMapping("/theater/{id}")
    ResponseEntity<? > replaceShow(@RequestBody Theater newShow,@PathVariable Long id){
        Theater updateShow= theaterRepository.findById(id)
                .map(theater -> {
                    theater.setMovieTime(newShow.getMovieTime());
                    theater.setMovieTicketPrice(newShow.getMovieTicketPrice());
                    return theaterRepository.save(theater);
                })
                .orElseGet(() -> {
                    newShow.setId(id);
                    return theaterRepository.save(newShow);
                });
        EntityModel<Theater> addShow=theaterAssembler.toModel(updateShow);
        return ResponseEntity
                .created(addShow.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(addShow);
    }

    @DeleteMapping("/theater/{id}")
    ResponseEntity<?> deleteShow(@PathVariable Long id){
         theaterRepository.deleteById(id);
         return ResponseEntity.noContent().build();
    }
}
