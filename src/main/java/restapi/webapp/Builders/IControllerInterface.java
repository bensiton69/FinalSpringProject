package restapi.webapp.Builders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IControllerInterface<T> {
    ResponseEntity<EntityModel<T>> getById(@PathVariable Long id);
    ResponseEntity<CollectionModel<EntityModel<T>>> getAsResponseEntity();
}
