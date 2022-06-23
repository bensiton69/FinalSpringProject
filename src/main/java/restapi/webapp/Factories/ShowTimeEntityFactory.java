//package restapi.webapp.Factories;
//
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
//import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import org.springframework.stereotype.Component;
//import restapi.webapp.Models.ShowTime;
//import restapi.webapp.controllers.ShowTimeController;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class ShowTimeEntityFactory implements SimpleRepresentationModelAssembler<ShowTime> {
//
//    @Override
//    public void addLinks(EntityModel<ShowTime> resource) {
//        resource.add(
//                WebMvcLinkBuilder.linkTo(methodOn(ShowTimeController.class).getShowTimeById(resource.getContent().getId()))
//                        .withSelfRel());
//
//        resource.add(
//                linkTo(methodOn(ShowTimeController.class).getShowTimesAsResponseEntity())
//                        .withRel("ST info")
//        );
//    }
//
//    @Override
//    public void addLinks(CollectionModel<EntityModel<ShowTime>> resources) {
//        resources.add(linkTo(methodOn(ShowTimeController.class).getShowTimesAsResponseEntity()).withSelfRel());
//
//    }
//}
