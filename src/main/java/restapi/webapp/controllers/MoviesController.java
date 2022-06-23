//package restapi.webapp.controllers;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import restapi.webapp.Builders.BuilderEntityFactory;
//import restapi.webapp.Builders.IControllerInterface;
//import restapi.webapp.Dtos.MovieDto;
//import restapi.webapp.Factories.MovieEntityFactory;
//import restapi.webapp.Mappers.MapperCinema;
//import restapi.webapp.Models.MovieLeg;
//import restapi.webapp.Repositories.MovieRepos;
//import restapi.webapp.Services.MovieService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@RestController
//public class MoviesController implements IControllerInterface<MovieDto> {
//    private final MovieRepos movieRepos;
//    private final MovieService movieService;
//    private final MovieEntityFactory movieEntityFactory;
//    private static final Logger logger = LoggerFactory.getLogger(MoviesController.class);
//
//
//    public MoviesController(MovieRepos movieRepos, MovieService movieService, MovieEntityFactory movieEntityFactory) {
//        this.movieRepos = movieRepos;
//        this.movieService = movieService;
//        this.movieEntityFactory = movieEntityFactory;
//    }
//
//    @GetMapping("/Movies/{id}")
//    public ResponseEntity<EntityModel<MovieLeg>> getMoviesById(@PathVariable Long id) {
//        return  movieRepos.findById(id)
//                .map(movieEntityFactory::toModel)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/MoviesAsList")
//    public List<MovieLeg> getMoviesAsList() {
//        return (List<MovieLeg>) movieRepos.findAll();
//    }
//
//    @GetMapping("/MoviesAsResponseEntity")
//    public ResponseEntity<CollectionModel<EntityModel<MovieLeg>>> getMoviesAsResponseEntity() {
//        return ResponseEntity.ok(movieEntityFactory.toCollectionModel(movieRepos.findAll()));
//    }
//
//    @GetMapping("/MoviesAsDto")
//    public ResponseEntity<List<MovieDto>> getMoviesAsDto() {
//        logger.info("--> getMoviesAsDto()");
//
//        List<MovieLeg> movieLegs = (List<MovieLeg>) movieRepos.findAll();
//        MovieLeg m = movieLegs.get(0);
////        MovieDto movieDto = MapperCinema.MapFromMovieToMovieGetDto(m);
//
//        return ResponseEntity.ok(
//                        StreamSupport.stream(movieRepos.findAll().spliterator(),
//                                        false)
//                                .map(MapperCinema::MapFromMovieToMovieGetDto) //
//                                .collect(Collectors.toList()));
//    }
//
//
//    @GetMapping("/MoviesAsTest")
//    public ResponseEntity<List<MovieDto>> MoviesAsTest() {
//        logger.info("--> MoviesAsTest()");
//
//        List<MovieLeg> movieLegs = (List<MovieLeg>) movieRepos.findAll();
//        MovieLeg m = movieLegs.get(0);
////        MovieDto movieDto = MapperCinema.MapFromMovieToMovieGetDto(m);
//
//        return ResponseEntity.ok(
//                StreamSupport.stream(movieRepos.findAll().spliterator(),
//                                false)
//                        .map(MapperCinema::MapFromMovieToMovieGetDto) //
//                        .collect(Collectors.toList()));
//    }
//
//    @GetMapping("/getById/{id}")
//    @Override
//    public ResponseEntity<EntityModel<MovieDto>> getById(Long id) {
//        BuilderEntityFactory<MovieDto> movieDtoBuilderEntityFactory = new BuilderEntityFactory<>(this);
//        return movieRepos.findById(id) // Post object
//                .map(MapperCinema::MapFromMovieToMovieGetDto) //
//                .map(movieDtoBuilderEntityFactory::toModel) //
//                .map(ResponseEntity::ok) //
//                .orElse(ResponseEntity.notFound().build());    }
//
//    @GetMapping("/getAsResponseEntity")
//    @Override
//    public ResponseEntity<CollectionModel<EntityModel<MovieDto>>> getAsResponseEntity() {
//        BuilderEntityFactory<MovieDto> movieDtoBuilderEntityFactory = new BuilderEntityFactory<>(this);
//        return ResponseEntity.ok(
//                movieDtoBuilderEntityFactory.toCollectionModel(
//                        StreamSupport.stream(movieRepos.findAll().spliterator(),
//                                        false)
//                                .map(MapperCinema::MapFromMovieToMovieGetDto) //
//                                .collect(Collectors.toList())));
//    }
//}
