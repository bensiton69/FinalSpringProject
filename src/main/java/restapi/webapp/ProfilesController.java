package restapi.webapp;

import org.apache.catalina.Manager;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
class ProfilesController {

    private final ProfileRepo repo;
    private final ProfileEntityFactory factory;

    ProfilesController(ProfileRepo repo, ProfileEntityFactory factory) {
        this.repo = repo;
        this.factory = factory;
    }

    @GetMapping("/profiles")
    ResponseEntity<CollectionModel<EntityModel<Profile>>> allProfiles() {
        return ResponseEntity.ok(
                factory.toCollectionModel(repo.findAll()));

    }

    @GetMapping("/profiles/{id}")
    ResponseEntity<EntityModel<Profile>> singleProfile(@PathVariable long id) {
        return repo.findById(id) //
                .map(factory::toModel) //
                .map(ResponseEntity::ok) //
                .orElse(ResponseEntity.notFound().build());
    }
}
