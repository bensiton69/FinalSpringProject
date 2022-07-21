package restapi.webapp.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.webapp.AsyncRunner;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.MovieGetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Movie REST controller
 * Implements IControllerInterface of MovieGetDto
 */
@RestController
public class MoviesController implements IControllerInterface<MovieGetDto> {
    private final MovieRepos movieRepos;
    private final MovieService movieService;
    private final AsyncRunner asyncRunner;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<MovieGetDto> movieEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);


    public MoviesController(MovieRepos movieRepos, MovieService movieService, AsyncRunner asyncRunner, IMapperCinema mapperCinema) {
        this.movieRepos = movieRepos;
        this.movieService = movieService;
        this.asyncRunner = asyncRunner;
        this.mapperCinema = mapperCinema;
        movieEntityFactory = new BuilderEntityFactory<>(this);
    }

    /**
     *  takes id as param and return single MovieGetDto
     * @param id movie id
     * @return ResponseEntity of EntityModel of MovieGetDto
     */
    @GetMapping("/Movies/{id}")
    @Override
    public ResponseEntity<EntityModel<MovieGetDto>> getById(@PathVariable Long id) {
        return movieRepos.findById(id)
                .map(mapperCinema::MapFromMovieToMovieGetDto)
                .map(movieEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * @return ResponseEntity of collection model of EntityModel of MovieGetDto
     */
    @GetMapping("/Movies")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<MovieGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                movieEntityFactory.toCollectionModel(
                        StreamSupport.stream(movieRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromMovieToMovieGetDto) //
                                .collect(Collectors.toList())));
    }

    /**
     * Demonstrate admin action
     * takes movie id as parameter and removes it from the repos
     * @param id movie id
     * @return response entity
     */
    @DeleteMapping("/Movies/AdminActions/Remove/{id}")
    public ResponseEntity removeMovie(@PathVariable Long id){
        if(movieRepos.existsById(id) == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            movieRepos.deleteById(id);
            logger.info("Movie" + id + "deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Demonstrate admin action
     * will apply to external api and save new movies using async runner
     * @return response entity
     */
    @PostMapping("/Movies/AdminActions/AddMovies")
    public ResponseEntity seedMovies(){
        try {
            asyncRunner.SaveMovies();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not seed new movies");
        }
        return ResponseEntity.ok("Seed new movies: success");
    }

    // TODO: delete?
    @PostMapping("/Movies/AdminActions/AddSingleMovie")
    public ResponseEntity seedSingleMovie(){
        try {
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not seed new movies");
        }
        return ResponseEntity.ok("Seed new movies: success");
    }
}
