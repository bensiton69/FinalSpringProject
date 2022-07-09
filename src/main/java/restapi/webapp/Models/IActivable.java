package restapi.webapp.Models;

import restapi.webapp.Enums.Status;

public interface IActivable {
    Status getStatus();
    void setStatus(Status active);
}
