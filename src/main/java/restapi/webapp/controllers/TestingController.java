package restapi.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Repositories.ShowTimeRepos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestingController {

    private static final Logger logger = LoggerFactory.getLogger(TestingController.class);

    private final MovieRepos movieRepos;
    private final ShowTimeRepos showTimeRepos;

    public TestingController(MovieRepos movieRepos, ShowTimeRepos showTimeRepos) {
        this.movieRepos = movieRepos;
        this.showTimeRepos = showTimeRepos;
    }

    @GetMapping("Test")
    public void getTest()
    {
        List<ShowTime> showTimes = (List<ShowTime>) showTimeRepos.findAll();
        int num = (int) showTimes.get(0).getSeatPackages().stream().count();

        logger.info("show time has " +num + " SP");
    }
}
