package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.webapp.Enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Reservation")
public class Reservation implements IActivable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double price;
    private LocalDateTime orderTime = LocalDateTime.now();
    private LocalDateTime startTime;
    private Status status = Status.Active;

    @OneToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    private List<SeatPackage> seatPackages = new ArrayList<SeatPackage>();

    @ManyToOne
    @JoinColumn(name = "costumerUser_id")
    private CostumerUser costumerUser;

    public Reservation(List<SeatPackage> seatPackages) {
        this.seatPackages = seatPackages;
        price = this.seatPackages.stream().count()* 3.4;
    }

    public Reservation(CostumerUser costumerUser) {
        this.costumerUser = costumerUser;
    }

    public void addSeatPackage(SeatPackage seatPackage){
        seatPackages.add(seatPackage);
    }


}
