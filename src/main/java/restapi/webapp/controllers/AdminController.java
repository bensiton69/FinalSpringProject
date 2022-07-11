package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.AsyncRunner;
import restapi.webapp.Models.Movie;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;
import restapi.webapp.Services.ShowTimeService;

import java.util.List;

@RestController
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final MovieService movieService;
    private final ShowTimeService showTimeService;
    private final MovieRepos movieRepos;
    private final AsyncRunner asyncRunner;

    public AdminController(MovieService movieService, ShowTimeService showTimeService, MovieRepos movieRepos, AsyncRunner asyncRunner) {
        this.movieService = movieService;
        this.showTimeService = showTimeService;
        this.movieRepos = movieRepos;
        this.asyncRunner = asyncRunner;
    }





}
