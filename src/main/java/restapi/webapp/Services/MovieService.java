package restapi.webapp.Services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import restapi.webapp.Models.Movie;

import java.util.concurrent.CompletableFuture;

@Service
public class MovieService {
    private RestTemplate template;

    public MovieService(RestTemplateBuilder restTemplateBuilder) {
        this.template = restTemplateBuilder.build();
    }


    @Async
    public CompletableFuture<Movie> singleMovie(int id){
        String urlTemplate = String.format("http://localhost:8081/Movies/%s", id);
        Movie aMovie = this.template.getForObject(urlTemplate,Movie.class);
        System.out.println(aMovie);
        /*
         return a CompletableFuture<GitHubUser> when the computation is done
         this goes hand-with-hand with the join() method
         */
        return CompletableFuture.completedFuture(aMovie);
    }

    @Async
    public CompletableFuture<Movie> Movies(){
        String urlTemplate = String.format("http://localhost:8081/Movies");
        Movie aMovie = this.template.getForObject(urlTemplate,Movie.class);
        System.out.println(aMovie);
        /*
         return a CompletableFuture<GitHubUser> when the computation is done
         this goes hand-with-hand with the join() method
         */
        return CompletableFuture.completedFuture(aMovie);
    }
}
