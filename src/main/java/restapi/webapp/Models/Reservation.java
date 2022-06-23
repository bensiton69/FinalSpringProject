//package restapi.webapp.Models;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Entity
//@NoArgsConstructor
//public class Reservation {
//    @Id
//    @GeneratedValue
//    private Long id;
//    private Double price;
//    private LocalDateTime orderTime;
//    private LocalDateTime startTime;
//
//    @OneToMany
//    List<SeatPackage> seatPackage = new ArrayList<SeatPackage>();
//
//    @ManyToOne
//    private CostumerUser costumerUser;
//
//    @OneToOne
//    private ShowTime showTime;
//
//}
