package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restapi.webapp.Models.Movie;

import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

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

    /**
     * will use the movie service and seed new movies to the database
     * @throws Exception
     */
        public void SaveMovies() throws Exception {
        CompletableFuture<Movie[]> moviesTask = movieService.Movies();
        CompletableFuture.allOf(moviesTask).join();
        Movie[] movies = moviesTask.get();
        List<Movie> moviesAsList = Arrays.asList(movies);

        moviesAsList.remove(0);
        movieRepos.saveAll(moviesAsList);
    }

    /**
     * will use the movie service and save single movie to the database
     * @throws Exception
     */
    public void SaveSingle(int id) throws Exception {
        CompletableFuture<Movie> movieTask = movieService.singleMovie(id);
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
