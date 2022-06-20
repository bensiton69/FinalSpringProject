package restapi.webapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
class PostDTOFactory implements SimpleRepresentationModelAssembler<PostDTO> {

    /**
     * Define links to add to every individual {@link EntityModel}.
     * @param resource
     */

    /*
    {
    "id": 2,
    "title": "title1",
    "greeting": "This is a DTO greeting",
    "_links": {
        "self": {
            "href": "http://localhost:8080/posts/2/info"
        },
        "single post": {
            "href": "http://localhost:8080/posts/2"
        },
        "posts information": {
            "href": "http://localhost:8080/posts/info"
        }
    }
}
     */
    @Override
    public void addLinks(EntityModel<PostDTO> resource) {
        resource.add(
                linkTo(methodOn(PostsController.class).postInfo(resource.getContent().getId()))
                        .withSelfRel());
         // Return DAO
//        resource.add(linkTo(methodOn(PostsController.class).singlePost(resource.getContent().getId()))
//                .withRel("single post"));

        resource.add(linkTo(methodOn(PostsController.class).allPostsInfo())
                .withRel("posts information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<PostDTO>> resources) {
        resources.add(linkTo(methodOn(PostsController.class).allPostsInfo()).withSelfRel());
    }
}
