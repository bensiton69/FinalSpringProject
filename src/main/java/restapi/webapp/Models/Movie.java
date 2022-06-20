package restapi.webapp.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import restapi.webapp.Enums.eGenre;
//import restapi.webapp.Enums.eGenre;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
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

//    @JsonIgnore
//    @Enumerated(EnumType.STRING)
//    private eGenre[] genre;

    @OneToMany
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
}
