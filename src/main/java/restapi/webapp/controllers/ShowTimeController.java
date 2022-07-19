package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.webapp.Builders.BuilderEntityFactory;
import restapi.webapp.Builders.IControllerInterface;
import restapi.webapp.Dtos.Get.ShowTimeGetDto;
import restapi.webapp.Dtos.KeyValuePair;
import restapi.webapp.Dtos.Set.ShowTimeSetDto;
import restapi.webapp.Mappers.IMapperCinema;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Repositories.ShowTimeRepos;
import restapi.webapp.Services.ActivationService;
import restapi.webapp.Services.ShowTimeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Reservation REST controller
 * Implements IControllerInterface of ShowTimeGetDto
 */
@RestController
public class ShowTimeController implements IControllerInterface<ShowTimeGetDto> {
    private final ShowTimeRepos showTimeRepos;
    private final MovieRepos movieRepos;
    private final ShowTimeService showTimeService;
    private final IMapperCinema mapperCinema;
    private final BuilderEntityFactory<ShowTimeGetDto> showTimeEntityFactory;
    private final ActivationService activationService;
    private static final Logger logger = LoggerFactory.getLogger(ShowTimeController.class);

    public ShowTimeController(ShowTimeRepos showTimeRepos, MovieRepos movieRepos, ShowTimeService showTimeService, IMapperCinema mapperCinema, ActivationService activationService) {
        this.showTimeRepos = showTimeRepos;
        this.movieRepos = movieRepos;
        this.showTimeService = showTimeService;
        this.mapperCinema = mapperCinema;
        this.activationService = activationService;
        this.showTimeEntityFactory = new BuilderEntityFactory<ShowTimeGetDto>(this);
    }

    /**
     * takes id as param and return single ShowTimeGetDto
     * @param id ShowTime id
     * @return ResponseEntity of EntityModel of ShowTimeGetDto
     */
    @GetMapping("/ShowTimes/{id}")
    @Override
    public ResponseEntity<EntityModel<ShowTimeGetDto>> getById(@PathVariable Long id) {
        return  showTimeRepos.findById(id)
                .map(mapperCinema::MapFromShowTimeToShowTimeDto)
                .map(showTimeEntityFactory::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * @return ResponseEntity of collection model of EntityModel of ShowTimeGetDto
     */
    @GetMapping("/ShowTimes")
    @Override
    public ResponseEntity<CollectionModel<EntityModel<ShowTimeGetDto>>> getAsResponseEntity() {
        return ResponseEntity.ok(
                showTimeEntityFactory.toCollectionModel(
                        StreamSupport.stream(showTimeRepos.findAll().spliterator(),
                                        false)
                                .map(mapperCinema::MapFromShowTimeToShowTimeDto) //
                                .collect(Collectors.toList())));
    }

    /**
     * returns
     * @param someParameter
     * @return
     */
    @GetMapping("/ShowTimes/AsKVP")
    @ResponseBody
    public List<?> getOptionalMessageWithLocalDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Optional<LocalDate> someParameter){
        return
                ((List<ShowTime>)showTimeRepos.findAll())
                        .stream()
                        .filter(st -> st.getStartTime().toLocalDate().isAfter(someParameter.get()) ||
                                st.getStartTime().toLocalDate().isEqual(someParameter.get()))
                        .map(showTime -> new KeyValuePair(showTime.getId(), showTime.getMovie().getName() + showTime.getStartTime()))
                        .collect(Collectors.toList());
    }

    @PostMapping("/ShowTimes/AdminActions/CreateShowTimes")
    public ResponseEntity seedShowTimes(){
        try {
            List<Movie> movies = ((List<Movie>)movieRepos.findAll());
            showTimeService.createNewShowTimes(movies);
            movieRepos.saveAll(movies);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not seed new movies");
        }
        return ResponseEntity.ok("Seed new movies: success");
    }

    @DeleteMapping("/ShowTimes/AdminActions/{id}")
    public ResponseEntity removeShowTime(@PathVariable Long id){
        if(showTimeRepos.existsById(id) == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            ShowTime showTime = showTimeRepos.findById(id).get();
            activationService.DeactivateWithProps(showTime,showTimeRepos);
            logger.info("ST" + id + "deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/ShowTimes/AdminActions/{id}")
    public ResponseEntity putShowTime(@PathVariable Long id, @RequestBody ShowTimeSetDto showTimeSetDto){
        if(movieRepos.existsById(showTimeSetDto.getMovieId()) == false
                || showTimeRepos.existsById(id) == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
        {
            ShowTime oldShowTime = showTimeRepos.findById(id).get();
            oldShowTime.setStartTime(showTimeSetDto.getDateTime());
            showTimeRepos.save(oldShowTime);
            logger.info("ST" + id + "saved");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/ShowTimes/AdminActions")
    public ResponseEntity putShowTime(@RequestBody ShowTimeSetDto showTimeSetDto){

        if(movieRepos.existsById(showTimeSetDto.getMovieId()) == false)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        ShowTime showTime = new ShowTime(showTimeSetDto.getDateTime(),
                movieRepos.findById(showTimeSetDto.getMovieId()).get());

        showTimeRepos.save(showTime);
        logger.info("ST" + showTime.getId() + "created");
        return new ResponseEntity<>(HttpStatus.CREATED);

    }



}

