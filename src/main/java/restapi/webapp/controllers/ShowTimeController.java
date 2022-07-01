package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.MovieDto;
import restapi.webapp.Dtos.ShowTimeDto;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Repositories.ShowTimeRepos;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class ShowTimeController implements IControllerInterface<ShowTimeDto> {
    private final ShowTimeRepos showTimeRepos;
    private final BuilderEntityFactory<ShowTimeDto> showTimeEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    public ShowTimeController(ShowTimeRepos showTimeRepos) {
        this.showTimeRepos = showTimeRepos;
        this.showTimeEntityFactory = new BuilderEntityFactory<ShowTimeDto>(this);
    }


    @GetMapping("/showTimes/getById/{id}")
    @Override
    public ResponseEntity<EntityModel<ShowTimeDto>> getById(Long id) {
        return  showTimeRepos.findById(id)
                .map(MapperCinema::MapFromShowTimeToShowTimeDto)
                .map(showTimeEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/showTimes/getAll")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ShowTimeDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                showTimeEntityFactory.toCollectionModel(
                        StreamSupport.stream(showTimeRepos.findAll().spliterator(),
                                        false)
                                .map(MapperCinema::MapFromShowTimeToShowTimeDto) //
                                .collect(Collectors.toList())));
    }
}

