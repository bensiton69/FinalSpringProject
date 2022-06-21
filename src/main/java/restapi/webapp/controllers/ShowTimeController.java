package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Dtos.MovieDto;
import restapi.webapp.Dtos.ShowTimeDto;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Models.ShowTime;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ShowTimeController {
    private final ShowTimeRepos showTimeRepos;
    private final ShowTimeEntityFactory showTimeEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    public ShowTimeController(ShowTimeRepos showTimeRepos, ShowTimeEntityFactory showTimeEntityFactory) {
        this.showTimeRepos = showTimeRepos;
        this.showTimeEntityFactory = showTimeEntityFactory;
    }

    @GetMapping("/ShowTimes/{id}")
    public ResponseEntity<EntityModel<ShowTime>> getShowTimeById(@PathVariable Long id){
        return  showTimeRepos.findById(id)
                .map(showTimeEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ShowTimesAsResponseEntity")
    public ResponseEntity<CollectionModel<EntityModel<ShowTime>>> getShowTimesAsResponseEntity(){
        return ResponseEntity.ok(showTimeEntityFactory.toCollectionModel(showTimeRepos.findAll()));
    }

    @GetMapping("/ShowTimesAsResponseEntityDTO")
    public ResponseEntity<List<ShowTimeDto>> getShowTimesAsResponseEntityDTO(){
        return ResponseEntity.ok(
                StreamSupport.stream(showTimeRepos.findAll().spliterator(),
                                false)
                        .map(MapperCinema::MapFromShowTimeToShowTimeDto)
                        .collect(Collectors.toList()));
    }

}

