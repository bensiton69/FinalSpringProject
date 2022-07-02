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
import restapi.webapp.Dtos.MovieGetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Mappers.MapperCinema;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class MoviesController implements IControllerInterface<MovieGetDto> {
    private final MovieRepos movieRepos;
    private final MovieService movieService;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<MovieGetDto> movieEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);


    public MoviesController(MovieRepos movieRepos, MovieService movieService, IMapperCinema mapperCinema) {
        this.movieRepos = movieRepos;
        this.movieService = movieService;
        this.mapperCinema = mapperCinema;
        movieEntityFactory = new BuilderEntityFactory<>(this);
    }

    @GetMapping("/movies/getById/{id}")
    @Override
    public ResponseEntity<EntityModel<MovieGetDto>> getById(@PathVariable Long id) {
        return movieRepos.findById(id)
                .map(mapperCinema::MapFromMovieToMovieGetDto)
                .map(movieEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/movies/getAll")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<MovieGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                movieEntityFactory.toCollectionModel(
                        StreamSupport.stream(movieRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromMovieToMovieGetDto) //
                                .collect(Collectors.toList())));
    }
}
