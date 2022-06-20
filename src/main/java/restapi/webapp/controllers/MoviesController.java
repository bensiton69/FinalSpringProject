package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Factories.MovieEntityFactory;
import restapi.webapp.Models.Movie;
import restapi.webapp.Post;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class MoviesController {
    private final MovieRepos movieRepos;
    private final MovieService movieService;
    private final MovieEntityFactory movieEntityFactory;
    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);


    public MoviesController(MovieRepos movieRepos, MovieService movieService, MovieEntityFactory movieEntityFactory) {
        this.movieRepos = movieRepos;
        this.movieService = movieService;
        this.movieEntityFactory = movieEntityFactory;
    }

    @GetMapping("/Movies/{id}")
    public ResponseEntity<EntityModel<Movie>> getMoviesById(@PathVariable Long id) {
        return  movieRepos.findById(id)
                .map(movieEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/MoviesAsList")
    public List<Movie> getMoviesAsList() {
        return (List<Movie>) movieRepos.findAll();
    }

    @GetMapping("/MoviesAsResponseEntity")
    public ResponseEntity<CollectionModel<EntityModel<Movie>>> getMoviesAsResponseEntity() {
        return ResponseEntity.ok(movieEntityFactory.toCollectionModel(movieRepos.findAll()));
    }
}
