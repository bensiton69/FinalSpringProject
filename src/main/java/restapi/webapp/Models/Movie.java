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
    //TODO: Transfer to enum
    private String[] genres;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ShowTime> showTimes = new ArrayList<ShowTime>();

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", duration=" + duration +
                ", yearOfPublish=" + yearOfPublish +
                ", genres=" + Arrays.toString(genres) +
                ", showTimes=" + showTimes +
                '}';
    }

    public Movie(String name, String link, int duration, int yearOfPublish, String[] genres) {
        this.name = name;
        this.link = link;
        this.duration = duration;
        this.yearOfPublish = yearOfPublish;
        this.genres = genres;
    }
}
