package restapi.webapp.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.controllers.MoviesController;

@Service
public class InitShowTimeService {
    private static final Logger logger = LoggerFactory.getLogger(InitShowTimeService.class);

    public InitShowTimeService(MovieRepos movieRepos) {
        this.movieRepos = movieRepos;
    }

    private final MovieRepos movieRepos;

    public void test()
    {
        logger.info("logging" + movieRepos.findAll());
    }

}
