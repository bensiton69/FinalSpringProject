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
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Repositories.UserRepos;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class UsersController implements IControllerInterface<CostumerUserGetDto> {
    private final UserRepos userRepos;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<CostumerUserGetDto> userEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    public UsersController(UserRepos userRepos, IMapperCinema mapperCinema) {
        this.userRepos = userRepos;
        this.mapperCinema = mapperCinema;
        this.userEntityFactory = new BuilderEntityFactory<CostumerUserGetDto>(this);
    }


    //TODO: consider using the abstract user / ICostumer interface

    @GetMapping("/Users/{id}")
    @Override
    public ResponseEntity<EntityModel<CostumerUserGetDto>> getById(@PathVariable Long id) {
        return userRepos.findById(id)
                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto)
                .map(userEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/Users/getAll")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CostumerUserGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                userEntityFactory.toCollectionModel(
                        StreamSupport.stream(userRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto) //
                                .collect(Collectors.toList())));
    }
}
