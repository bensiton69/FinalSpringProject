package restapi.webapp.Factories;

import org.springframework.stereotype.Component;
import restapi.webapp.Models.Movie;
import restapi.webapp.SimpleIdentifiableRepresentationModelAssembler;
import restapi.webapp.controllers.MoviesController;

@Component
public class MovieEntityFactory extends SimpleIdentifiableRepresentationModelAssembler<Movie> {
    public MovieEntityFactory(){super(MoviesController.class);}
}
