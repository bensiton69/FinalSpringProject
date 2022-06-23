//package restapi.webapp.controllers;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//import restapi.webapp.Factories.UserEntityFactory;
//import restapi.webapp.Models.CostumerUser;
//import restapi.webapp.Repositories.UserRepos;
//
//@RestController
//public class UsersController {
//    private final UserRepos userRepos;
//    private final UserEntityFactory userEntityFactory;
//    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
//
//    public UsersController(UserRepos userRepos, UserEntityFactory userEntityFactory) {
//        this.userRepos = userRepos;
//        this.userEntityFactory = userEntityFactory;
//    }
//
//
//    //TODO: consider using the abstract user / ICostumer interface
//    @GetMapping("/Users/{id}")
//    public ResponseEntity<EntityModel<CostumerUser>> getUserById(@PathVariable Long id) {
//        return  userRepos.findById(id)
//                .map(userEntityFactory::toModel)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/UsersAsResponseEntity")
//    public ResponseEntity<CollectionModel<EntityModel<CostumerUser>>> getUsersAsResponseEntity() {
//        return ResponseEntity.ok(userEntityFactory.toCollectionModel(userRepos.findAll()));
//    }
//}
