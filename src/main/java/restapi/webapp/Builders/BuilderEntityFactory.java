package restapi.webapp.Builders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import restapi.webapp.controllers.MoviesController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class BuilderEntityFactory <T extends IHasId> implements SimpleRepresentationModelAssembler<T> {
    private final IControllerInterface classController;
    public BuilderEntityFactory(IControllerInterface classController) {
        this.classController = classController;
    }

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

    @Override
    public void addLinks(CollectionModel<EntityModel<T>> resources) {
        resources.add(linkTo(methodOn(classController.getClass()).getAsResponseEntity()).withSelfRel());

    }
}
