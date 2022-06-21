package restapi.webapp.controllers;

import org.springframework.data.repository.CrudRepository;
import restapi.webapp.Models.ShowTime;

public interface ShowTimeRepos extends CrudRepository<ShowTime, Long> {
}
