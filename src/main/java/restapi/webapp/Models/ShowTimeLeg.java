//package restapi.webapp.Models;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data // For Lombok auto-generated methods
//@Entity // JPA annotation to make the object storable SQL-based DB
//@NoArgsConstructor
//public class ShowTimeLeg {
//
//    @Id
//    @GeneratedValue
//    private Long id;
//    private LocalDateTime startTime;
//
//    @ManyToOne
//    private MovieLeg movieLeg;
//
//    @OneToMany(mappedBy = "showTimeLeg")
//    private List<SeatPackage> seatPackages;
//
//
//    public ShowTimeLeg(LocalDateTime startTime, MovieLeg movieLeg) {
//        this.startTime = startTime;
//        this.movieLeg = movieLeg;
//    }
//
//    @Override
//    public String toString() {
//        return "ShowTime{" +
//                "id=" + id +
//                ", startTime=" + startTime +
//                ", movie=" + movieLeg +
//                '}';
//    }
//
//    public void AddSeatPackage(SeatPackage seatPackage){
//        seatPackages.add(seatPackage);
//    }
//}
