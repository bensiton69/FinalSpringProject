//package restapi.webapp.Factories;
//
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.stereotype.Component;
//import restapi.webapp.Models.MovieLeg;
//import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
//import restapi.webapp.controllers.MoviesController;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class MovieEntityFactory implements SimpleRepresentationModelAssembler<MovieLeg> {
//    @Override
//    public void addLinks(EntityModel<MovieLeg> resource) {
//        resource.add(
//                linkTo(methodOn(MoviesController.class).getMoviesById(resource.getContent().getId()))
//                        .withSelfRel());
//
//        resource.add(
//                linkTo(methodOn(MoviesController.class).getMoviesAsResponseEntity())
//                        .withRel("Movies info")
//        );
//    }
//
//    @Override
//    public void addLinks(CollectionModel<EntityModel<MovieLeg>> resources) {
//        resources.add(linkTo(methodOn(MoviesController.class).getMoviesAsResponseEntity()).withSelfRel());
//    }
//}
