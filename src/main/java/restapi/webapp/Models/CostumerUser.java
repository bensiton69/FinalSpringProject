package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "CostumerUser")
public class CostumerUser {
    //TODO: move Reservations to interface ICostumer


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "costumerUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Reservation> reservations= new ArrayList<>();

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


//    public CostumerUser(String username) {
//        super(username);}

//    @Override
//    public String toString() {
//        return super.toString() +
//                "CostumerUser{" +
////                "Reservations=" + Reservations +
//                '}';
//    }
}



