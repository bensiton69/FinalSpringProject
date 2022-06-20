package restapi.webapp.NathanClasses;

import org.springframework.stereotype.Component;

@Component
// HATEOAS factory that converts Post objects into EntityModel<Post> objects.
class PostEntityFactory extends SimpleIdentifiableRepresentationModelAssembler<Post> {

    public PostEntityFactory() {
        super(PostsController.class);
    }
}