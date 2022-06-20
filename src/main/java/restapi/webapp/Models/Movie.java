package restapi.webapp.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.webapp.Enums.eGenre;
//import restapi.webapp.Enums.eGenre;

import javax.persistence.*;
import java.util.ArrayList;
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

//    @Enumerated(EnumType.STRING)
//    private eGenre[] genre = new eGenre[]();

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
                ", showTimes=" + showTimes +
                '}';
    }
}
