package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double price;
    private LocalDateTime orderTime = LocalDateTime.now();
    private LocalDateTime startTime;

//    @ManyToOne
//    @JoinColumn(name = "showTime_id")
//    private ShowTime showTime;

    @OneToMany(
            cascade = CascadeType.MERGE,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<SeatPackage> seatPackages = new ArrayList<SeatPackage>();

    @ManyToOne
    @JoinColumn(name = "costumerUser_id")
    private CostumerUser costumerUser;

    public Reservation(List<SeatPackage> seatPackages, LocalDateTime startTime) {
        this.seatPackages = seatPackages;
        price = this.seatPackages.stream().count()* 3.4;


        // TODO : start time = showtime.startTime
        this.startTime = startTime;
    }

    public Reservation(CostumerUser costumerUser, ShowTime showTime) {
        this.costumerUser = costumerUser;
    }

    public void addSeatPackage(SeatPackage seatPackage){
        seatPackages.add(seatPackage);
    }

}
