package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restapi.webapp.Models.Movie;

import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Component
public class AsyncRunner implements CommandLineRunner {
    private final MovieService movieService;
    private final MovieRepos movieRepos;
    private static final Logger classLogger = LoggerFactory.getLogger(AsyncRunner.class);

    public AsyncRunner( MovieService movieService, MovieRepos movieRepos) {
        this.movieService = movieService;
        this.movieRepos = movieRepos;
    }

    @Override
    public void run(String... args) throws Exception {

        CompletableFuture<Movie> movie = movieService.singleMovie(1);

        CompletableFuture<Movie>[] taskArrayMovie = new CompletableFuture[1];

        taskArrayMovie[0] = movie;

        CompletableFuture.allOf(taskArrayMovie).join();


        Movie mov = movie.get();
        classLogger.info("movie = " + mov);

        movieRepos.save(mov);
    }

    public void Seed() throws Exception {
        CompletableFuture<Movie> movie = movieService.singleMovie(2);

        CompletableFuture<Movie>[] taskArrayMovie = new CompletableFuture[1];

        taskArrayMovie[0] = movie;

        CompletableFuture.allOf(taskArrayMovie).join();


        Movie mov = movie.get();
        mov.setShowTimes(new ArrayList<ShowTime>());
        classLogger.info("movie = " + mov);

        movieRepos.save(mov);

    }
}
