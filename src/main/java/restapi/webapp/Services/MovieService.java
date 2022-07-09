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
        Movie aMovieLeg = this.template.getForObject(urlTemplate, Movie.class);
        return CompletableFuture.completedFuture(aMovieLeg);
    }

    @Async
    public CompletableFuture<Movie[]> Movies(){
        String urlTemplate = String.format("http://localhost:8081/Movies");
        Movie[] movies = this.template.getForObject(urlTemplate, Movie[].class);
        return CompletableFuture.completedFuture(movies);
    }
}
