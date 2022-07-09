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
import java.util.Arrays;
import java.util.List;
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

        CompletableFuture<Movie> movieTask = movieService.singleMovie(1);
        CompletableFuture<Movie[]> moviesTask = movieService.Movies();

        CompletableFuture<?>[] taskArrayMovie = new CompletableFuture[2];

        taskArrayMovie[0] = movieTask;
        taskArrayMovie[1] = moviesTask;

        CompletableFuture.allOf(taskArrayMovie).join();


        Movie movie = movieTask.get();
        Movie[] movies = moviesTask.get();
        classLogger.info("movie = " + movie);
        for (Movie m : movies)
        {
            classLogger.info("movies = " + m);
            movieRepos.save(m);
        }


        movieRepos.save(movie);
    }


        public void SeedMovies() throws Exception {
        CompletableFuture<Movie[]> moviesTask = movieService.Movies();
        CompletableFuture.allOf(moviesTask).join();
        Movie[] movies = moviesTask.get();
        List<Movie> moviesAsList = Arrays.asList(movies);

        moviesAsList.remove(0);
        movieRepos.saveAll(moviesAsList);


//        movieRepos.save(movies[0]);
//        for (Movie m : movies)
//        {
//            classLogger.info("movies = " + m);
//            movieRepos.save(m);
//        }
    }

    public void SeedSingle() throws Exception {
        CompletableFuture<Movie> movieTask = movieService.singleMovie(3);
        CompletableFuture<?>[] taskArrayMovie = new CompletableFuture[1];

        taskArrayMovie[0] = movieTask;

        CompletableFuture.allOf(taskArrayMovie).join();


        Movie movie = movieTask.get();
        classLogger.info("movie = " + movie);

        movie = movieTask.get();
        classLogger.info("movie = " + movie);

        movieRepos.save(movie);
    }

}
