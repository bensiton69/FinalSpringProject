package restapi.webapp.Builders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
//import restapi.webapp.controllers.MoviesController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 *  this builder is gets T and return implementation of SimpleRepresentationModelAssembler
 * @param <T> represents some resource that implements the IHasId interface
 */
public class BuilderEntityFactory <T extends IHasId> implements SimpleRepresentationModelAssembler<T> {

    private final IControllerInterface classController;
    public BuilderEntityFactory(IControllerInterface classController) {
        this.classController = classController;
    }

    /**
     * This method will add self rel and collection rel
     * @param resource
     */
    @Override
    public void addLinks(EntityModel<T> resource) {

        resource.add(
                linkTo(methodOn(classController.getClass()).getById(resource.getContent().getId()))
                        .withSelfRel());

        resource.add(
                linkTo(methodOn(classController.getClass()).getAsResponseEntity())
                        .withRel("Collection info:")
        );
    }

    /**
     * this method will add only collection link
     * @param resources
     */
    @Override
    public void addLinks(CollectionModel<EntityModel<T>> resources) {
        resources.add(linkTo(methodOn(classController.getClass()).getAsResponseEntity()).withSelfRel());

    }
}
