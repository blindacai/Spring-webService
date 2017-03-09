package Query;

import Tables.Database;
import Tables.Movie;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by linda on 3/8/2017.
 */
@RestController
public class Retrieve {
    private Database database;

    public Retrieve(){
        this.database = new Database();
    }

    @RequestMapping("/movie")
    public Movie getMovie() throws SQLException {
        return database.getMovie("Coming Home");
    }

    @RequestMapping("/allmovies")
    public List<Movie> getAllMovies() throws SQLException {
        return database.getAllMovies();
    }
}
