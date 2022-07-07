package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
// TODO: change table name to ShowTime
@Table(name = "showTime")
@NoArgsConstructor
public class ShowTime {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime startTime;


    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(
            mappedBy = "showTime",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<SeatPackage> seatPackages = new ArrayList<>();

    public ShowTime(LocalDateTime startTime, Movie movie) {
        this.startTime = startTime;
        this.movie = movie;
    }

    public void AddSeatPackage(SeatPackage seatPackage){
        seatPackages.add(seatPackage);
    }
}
