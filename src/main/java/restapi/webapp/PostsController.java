package restapi.webapp;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
class PostsController {
    private final PostRepo postRepo;
    private final PostEntityFactory postFactory;
    private final PostDTOFactory postDTOFactory;

    PostsController(PostRepo postRepo, PostEntityFactory postFactory, PostDTOFactory postDTOFactory){
        this.postRepo = postRepo;
        this.postFactory = postFactory;
        this.postDTOFactory = postDTOFactory;
    }

    @GetMapping("/posts/")
    public ResponseEntity<CollectionModel<EntityModel<Post>>> allPosts() {
        return ResponseEntity.ok(postFactory.toCollectionModel(postRepo.findAll()));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<EntityModel<Post>> singlePost(@PathVariable long id) {

        return postRepo.findById(id) //
                .map(postFactory::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/profiles/{id}/posts")
    public ResponseEntity<CollectionModel<EntityModel<Post>>> profilePosts(@PathVariable long id) {
        return ResponseEntity.ok(
                postFactory.toCollectionModel(postRepo.findByProfileId(id)));
    }

    @GetMapping("/posts/info")
    public ResponseEntity<CollectionModel<EntityModel<PostDTO>>> allPostsInfo() {
        return ResponseEntity.ok(
                postDTOFactory.toCollectionModel(
                        StreamSupport.stream(postRepo.findAll().spliterator(),
                                false)
                                .map(PostDTO::new) //
                                .collect(Collectors.toList())));
    }

    @GetMapping("/posts/{id}/info")
    public ResponseEntity<EntityModel<PostDTO>> postInfo(@PathVariable Long id) {
        return postRepo.findById(id) // Post object
                .map(PostDTO::new) //
                .map(postDTOFactory::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }

}
