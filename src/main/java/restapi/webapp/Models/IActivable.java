package restapi.webapp.Models;

public interface IActivable {
    Status getStatus();
    void setStatus(Status active);
}
