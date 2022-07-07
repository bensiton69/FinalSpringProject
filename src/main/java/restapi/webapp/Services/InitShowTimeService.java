package restapi.webapp.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import restapi.webapp.Models.Movie;
import restapi.webapp.Models.SeatPackage;
import restapi.webapp.Models.ShowTime;
import restapi.webapp.Repositories.MovieRepos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitShowTimeService {
    private static final Logger logger = LoggerFactory.getLogger(InitShowTimeService.class);

    public ShowTime InitShowTime(Movie movie, int numOfRows, int numOfCols, LocalDateTime startTime){
        ShowTime showTime = new ShowTime(startTime, movie);
        List<SeatPackage> seatPackages = new ArrayList<>();

        for (int i=0;i<numOfRows;i++)
            for (int j=0;j<numOfCols;j++)
                seatPackages.add(new SeatPackage(i,j, true, showTime));
        showTime.setSeatPackages(seatPackages);
        movie.addShowTime(showTime);
        return showTime;
    }

}
