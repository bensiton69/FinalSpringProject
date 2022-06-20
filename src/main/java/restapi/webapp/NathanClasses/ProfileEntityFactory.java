package restapi.webapp.NathanClasses;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class ProfileEntityFactory extends SimpleIdentifiableRepresentationModelAssembler<Profile> {
    public ProfileEntityFactory() {
        super(ProfilesController.class);
    }

    @Override
        public void addLinks(EntityModel<Profile> resource) {
        super.addLinks(resource);

        Optional<Long> tempid = Optional.ofNullable(resource.getContent().getId());
        tempid.ifPresent(id -> { //
                    // Add custom link to find all managed employees
                    resource.add(linkTo(methodOn(PostsController.class).profilePosts(id)).withRel("employees"));
        });
    }


    @Override
    public void addLinks(CollectionModel<EntityModel<Profile>> resources) {

        super.addLinks(resources);

        resources.add(linkTo(methodOn(PostsController.class).allPosts()).withRel("employees"));
        resources.add(linkTo(methodOn(PostsController.class).allPostsInfo()).withRel("detailedEmployees"));
    }
}
