package restapi.webapp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.CostumerUserGetDto;
import restapi.webapp.Dtos.Set.CostumerUserSetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Models.CostumerUser;
import restapi.webapp.Enums.Status;
import restapi.webapp.Repositories.UserRepos;
import restapi.webapp.Services.ActivationService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class UsersController implements IControllerInterface<CostumerUserGetDto> {
    private final UserRepos userRepos;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<CostumerUserGetDto> userEntityFactory;
    private final ActivationService activationService;
    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    public UsersController(UserRepos userRepos, IMapperCinema mapperCinema, ActivationService activationService) {
        this.userRepos = userRepos;
        this.mapperCinema = mapperCinema;
        this.activationService = activationService;
        this.userEntityFactory = new BuilderEntityFactory<CostumerUserGetDto>(this);
    }

    /**
     * GET endpoint to find UserById
     * @param id
     * @return user with id
     */
    @GetMapping("/Users/{id}")
    @Override
    public ResponseEntity<EntityModel<CostumerUserGetDto>> getById(@PathVariable Long id) {
        return userRepos.findById(id)
                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto)
                .map(userEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    /**
     * GET endpoint to find all Users
     * @return all users
     */
    @GetMapping("/Users")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<CostumerUserGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                userEntityFactory.toCollectionModel(
                        StreamSupport.stream(userRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto) //
                                .collect(Collectors.toList())));
    }

    /**
     * GET endpoint to find all active Users
     * @return all active users
     */
    @GetMapping("/Users/Active")
    public ResponseEntity<CollectionModel<EntityModel<CostumerUserGetDto>>> getActiveUsers() {
        return ResponseEntity.ok(
                userEntityFactory.toCollectionModel(
                        StreamSupport.stream(((List<CostumerUser>)userRepos.findAll())
                                                .stream()
                                                .filter(u-> u.getStatus() == Status.Active)
                                                .collect(Collectors.toList())
                                                .spliterator(),
                                        false)
                                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto) //
                                .collect(Collectors.toList())));
    }

    /**
     * GET endpoint to find all Users who have reservations
     * @return all users who have reservations
     */
    @GetMapping("/UsersWithReservations")
    public ResponseEntity<CollectionModel<EntityModel<CostumerUserGetDto>>> getUsersWithReservations() {

        return ResponseEntity.ok(
                userEntityFactory.toCollectionModel(
                        StreamSupport.stream(((List<CostumerUser>)userRepos.findAll())
                                                .stream()
                                                .filter(u-> u.getReservations().isEmpty() == false)
                                                .collect(Collectors.toList())
                                                .spliterator(),
                                        false)
                                .map(mapperCinema::MapFromCostumerUserToCostumerUserGetDto) //
                                .collect(Collectors.toList())));
    }

    /**
     * POST endpoint to create new User
     * @param userSetDto
     * @return ResponseEntity
     */
    @PostMapping("/Users")
    public ResponseEntity<?> newProduct(@RequestBody CostumerUserSetDto userSetDto){
        CostumerUser newUser = mapperCinema.MapFromCostumerUserSetDtoToCostumerUser(userSetDto);
        try{
            CostumerUserGetDto costumerUserGetDto = mapperCinema.MapFromCostumerUserToCostumerUserGetDto(
                    userRepos.save(newUser)
            );
            EntityModel<CostumerUserGetDto> entityUser = userEntityFactory.toModel(costumerUserGetDto);
            return
                    // status code 201
                    ResponseEntity.created(new URI(entityUser.getRequiredLink(IanaLinkRelations.SELF)
                            .getHref())).body(entityUser);
        } catch (Exception e) {
            return
                    // status code: 400
                    ResponseEntity.badRequest().body("Cannot create the product corresponding to " + userSetDto);
        }
    }

    /**
     * PUT endpoint to edit User
     * @param id
     * @param costumerUserSetDto
     */
    @PutMapping("/Users/{id}")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody CostumerUserSetDto costumerUserSetDto){
        CostumerUser costumerUser =userRepos.findById(id).get();
        if (costumerUser == null)
            return ResponseEntity.notFound().build();

        costumerUser.setUsername(costumerUserSetDto.getUsername());

        CostumerUserGetDto costumerUserGetDto = mapperCinema.MapFromCostumerUserToCostumerUserGetDto(
                userRepos.save(costumerUser)
        );

        EntityModel<CostumerUserGetDto> entityUser = userEntityFactory.toModel(costumerUserGetDto);
        try {
            return
                    // status code 201
                    ResponseEntity.created(new URI(entityUser.getRequiredLink(IanaLinkRelations.SELF)
                            .getHref())).body(entityUser);
        } catch (URISyntaxException e) {
            // status code: 400
            return
                    ResponseEntity.badRequest().body("Cannot create the product corresponding to " + costumerUserSetDto);
        }
    }

    /**
     * PUT endpoint to delete User
     * @param id
     */
    @DeleteMapping("Users/AdminActions/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        if(userRepos.findById(id).isPresent() == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            activationService.Deactivate(userRepos.findById(id).get(), userRepos);
            logger.info("User: " + id + "is no longer active");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
