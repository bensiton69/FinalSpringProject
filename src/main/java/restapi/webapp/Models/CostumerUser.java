package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.webapp.Enums.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "CostumerUser")
public class CostumerUser implements ICostumer, IActivable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String username;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "costumerUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reservation> reservations= new ArrayList<>();
    private Status status = Status.Active;
    public CostumerUser(String username) {

//        this.reservations = new ArrayList<>();
        this.username = username;
    }

    public void addReservation(Reservation reservation){
        reservations.add(reservation);
    }

    public void removeReservation(Reservation reservation) {
        reservations.remove(reservation);
    }
}



