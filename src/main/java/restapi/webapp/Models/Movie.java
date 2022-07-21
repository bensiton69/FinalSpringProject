package restapi.webapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "Movie")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String link;
    private int duration;
    private int yearOfPublish;
    private String[] genres;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    private List<ShowTime> showTimes = new ArrayList<ShowTime>();


    public Movie(String name, String link, int duration, int yearOfPublish, String[] genres) {
        this.name = name;
        this.link = link;
        this.duration = duration;
        this.yearOfPublish = yearOfPublish;
        this.genres = genres;
    }

    public void addShowTime(ShowTime showTime){
        showTimes.add(showTime);
    }

    public void removeShowTime(ShowTime showTime){
        if (showTimes.contains(showTime))
            showTimes.remove(showTime);
    }
}
