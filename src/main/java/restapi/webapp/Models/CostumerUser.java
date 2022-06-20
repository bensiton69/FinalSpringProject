package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class CostumerUser extends AbstractUser {
    //TODO: move Reservations to interface

    @OneToMany
    List<Reservation> Reservations = new ArrayList<Reservation>();

    public CostumerUser(Long id, String username) {
        super(id, username);}

}



