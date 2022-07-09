package restapi.webapp.Services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.IActivable;
import restapi.webapp.Enums.Status;

@Service
public class ActivationService {

    /***
     * Makes some entity active, and save it to DB
     * @param entity - the entity to active
     * @param crudRepository - the DB repository
     */
    public void Activate(IActivable entity, CrudRepository crudRepository){
        entity.setStatus(Status.Active);
        crudRepository.save(entity);
    }

    /***
     * Makes some entity inactive, and save it to DB
     * @param entity - the entity to inactive
     * @param crudRepository - the DB repository
     */
    public void Deactivate(IActivable entity, CrudRepository crudRepository){
        entity.setStatus(Status.Inactive);
        crudRepository.save(entity);
    }


}
