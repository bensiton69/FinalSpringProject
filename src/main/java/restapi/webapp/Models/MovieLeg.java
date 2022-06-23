//package restapi.webapp.Models;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
////import restapi.webapp.Enums.eGenre;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Data
//@Entity
//@NoArgsConstructor
//@EnableTransactionManagement
//@JsonIgnoreProperties(ignoreUnknown = true)
//public class MovieLeg {
//    @Id
//    @GeneratedValue
//    private Long id;
//    private String name;
//    private String link;
//    private int duration;
//    private int yearOfPublish;
//
//    //TODO: Transfer to enum
//    private String[] genres;
//
////    @JsonIgnore
////    @Enumerated(EnumType.STRING)
////    private eGenre[] genres;
//
//    @OneToMany
//    private List<ShowTimeLeg> showTimeLegs = new ArrayList<ShowTimeLeg>();
//
//
//    @Override
//    public String toString() {
//        return "Movie{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", link='" + link + '\'' +
//                ", duration=" + duration +
//                ", yearOfPublish=" + yearOfPublish +
//                ", genres=" + Arrays.toString(genres) +
//                ", showTimes=" + showTimeLegs +
//                '}';
//    }
//
//    public void addShowTimes(ShowTimeLeg showTimeLeg) {
//        showTimeLegs.add(showTimeLeg);
//    }
//}
