package restapi.webapp.NathanClasses;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.NathanClasses.Profile;

public interface ProfileRepo extends CrudRepository<Profile, Long> {

}