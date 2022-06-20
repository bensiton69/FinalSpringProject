package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public abstract class AbstractUser {
    @Id
    @GeneratedValue
    private Long id;
    private String username;

    public AbstractUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
