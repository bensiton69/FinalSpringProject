//package restapi.webapp.Factories;
//
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//import restapi.webapp.Models.CostumerUser;
//import restapi.webapp.controllers.UsersController;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class UserEntityFactory implements SimpleRepresentationModelAssembler<CostumerUser> {
//    @Override
//    public void addLinks(EntityModel<CostumerUser> resource) {
//        resource.add(
//                linkTo(methodOn(UsersController.class).getUserById(resource.getContent().getId()))
//                        .withSelfRel());
//
//        resource.add(
//                linkTo(methodOn(UsersController.class).getUsersAsResponseEntity())
//                        .withRel("Users info")
//        );
//    }
//
//    @Override
//    public void addLinks(CollectionModel<EntityModel<CostumerUser>> resources) {
//        resources.add(linkTo(methodOn(UsersController.class).getUsersAsResponseEntity()).withSelfRel());
//
//    }
//}
