package restapi.webapp.Services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.IActivable;
import restapi.webapp.Models.Status;

@Service
public class ActivationService {
    public void Activate(IActivable entity, CrudRepository crudRepository){
        entity.setStatus(Status.Active);
        crudRepository.save(entity);
    }

    public void Deactivate(IActivable entity, CrudRepository crudRepository){
        entity.setStatus(Status.Inactive);
        crudRepository.save(entity);
    }


}
