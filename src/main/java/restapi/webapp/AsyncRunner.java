package restapi.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import restapi.webapp.Models.Movie;
import restapi.webapp.Repositories.MovieRepos;
import restapi.webapp.Services.MovieService;

import java.util.concurrent.CompletableFuture;

@Component
public class AsyncRunner implements CommandLineRunner {
    private final UserService userService;
    private final MovieService movieService;
    private final MovieRepos movieRepos;
    private static final Logger classLogger = LoggerFactory.getLogger(AsyncRunner.class);

    public AsyncRunner(UserService userService, MovieService movieService, MovieRepos movieRepos) {
        this.userService = userService;
        this.movieService = movieService;
        this.movieRepos = movieRepos;
    }

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture<GitHubUser> user1 = userService.userDetails("octocat");
        CompletableFuture<GitHubUser> user2 = userService.userDetails("andrew");
        CompletableFuture<GitHubUser> user3 = userService.userDetails("HugoGiraudel");

        CompletableFuture<Movie> movie = movieService.singleMovie(1);

        CompletableFuture<GitHubUser>[] taskArray = new CompletableFuture[3];
        CompletableFuture<Movie>[] taskArrayMovie = new CompletableFuture[1];

        taskArray[0] = user1;
        taskArray[1] = user2;
        taskArray[2] = user3;

        taskArrayMovie[0] = movie;

        CompletableFuture.allOf(taskArray);
        CompletableFuture.allOf(taskArrayMovie);
        /*
        allOf - executes multiple CompletableFuture objects in parallel
        join - return the result values when complete opr throw an unchecked exception
         */
        CompletableFuture.allOf(user1,user2,user3).join();
        CompletableFuture.allOf(taskArrayMovie).join();

        classLogger.info("User1 = " + user1.get());
        classLogger.info("User2 = " + user2.get());
        classLogger.info("User3 = " + user3.get());

        Movie mov = movie.get();
        classLogger.info("movie = " + mov);

        movieRepos.save(mov);
//        classLogger.info("movie from db: " + movieRepos.findById(mov.getId()));



    }
}
