package com.PS.Controller;


import com.PS.CustomException.ShowNotFoundException;
import com.PS.Model.Theater;
import com.PS.Repository.TheaterRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class TheaterController {

    private final TheaterRepository theaterRepository;

    public TheaterController(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @GetMapping("/theater")
    public CollectionModel<EntityModel<Theater>> allShow(){

        List<EntityModel<Theater>> entityModels = (List<EntityModel<Theater>>) theaterRepository.findAll()
                .stream()
                .map(theater ->EntityModel.of(theater,
                        linkTo(methodOn(TheaterController.class).oneShow(theater.getId())).withSelfRel(),
                        linkTo(methodOn(TheaterController.class).allShow()).withRel("All Shows ")))
                        .collect(Collectors.toList());
        return CollectionModel.of(entityModels,linkTo(methodOn(TheaterController.class).allShow()).withSelfRel());
    }

    @GetMapping("/theater/{id}")
    public EntityModel<Theater> oneShow(@PathVariable Long id){
        Theater theater =theaterRepository.findById(id)
                .orElseThrow(() -> new ShowNotFoundException(id));
        return EntityModel.of(theater,
                linkTo(methodOn(TheaterController.class).oneShow(id)).withSelfRel(),
                linkTo(methodOn(TheaterController.class).allShow()).withRel("ALl Shows "));

    }

    @PostMapping("/theater")
    Theater newShow(@RequestBody Theater newShow){
        return theaterRepository.save(newShow);
    }

    @PutMapping("/theater/{id}")
    Theater replaceShow(@RequestBody Theater newShow,@PathVariable Long id){
        return theaterRepository.findById(id)
                .map(theater -> {
                    theater.setMovieTime(newShow.getMovieTime());
                    theater.setTicketPrice(newShow.getTicketPrice());
                    return theaterRepository.save(theater);
                })
                .orElseGet(() -> {
                    newShow.setId(id);
                    return theaterRepository.save(newShow);
                });
    }

    @DeleteMapping("/theater/{id}")
    void deleteShow(@PathVariable Long id){
         theaterRepository.deleteById(id);
    }
}
