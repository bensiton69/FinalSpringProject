package restapi.webapp.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
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
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "Showtime_id")
    private List<SeatPackage> seatPackages;


    public ShowTime(LocalDateTime startTime, Movie movie) {
        this.startTime = startTime;
        this.movie = movie;
    }
}
