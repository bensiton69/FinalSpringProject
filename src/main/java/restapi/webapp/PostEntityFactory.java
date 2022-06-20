package restapi.webapp;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
// HATEOAS factory that converts Post objects into EntityModel<Post> objects.
class PostEntityFactory extends SimpleIdentifiableRepresentationModelAssembler<Post> {

    public PostEntityFactory() {
        super(PostsController.class);
    }
}