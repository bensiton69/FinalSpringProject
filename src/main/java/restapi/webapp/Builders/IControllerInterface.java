package restapi.webapp.Builders;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * this is interface that used with BuilderEntityFactory class
 * it guarantee to the builder that a controller has some methods as presented here
 * each controller has to implement this interface in order to use the builder class
 * @param <T>
 */
public interface IControllerInterface<T> {
    ResponseEntity<EntityModel<T>> getById(@PathVariable Long id);
    ResponseEntity<CollectionModel<EntityModel<T>>> getAsResponseEntity();
}
