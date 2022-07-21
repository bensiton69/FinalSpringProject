package restapi.webapp.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import restapi.webapp.Models.Movie;

import java.awt.*;
import java.util.Collection;
import java.util.List;


@Repository
public interface MovieRepos extends CrudRepository<Movie, Long> {
    Movie findMovieByName(String name);
    List<Movie> findMovieByGenres(String genre);
    List<Movie> findMovieByDuration(int duration);
}


