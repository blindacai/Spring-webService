package Query;

import Tables.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/allmovies", method = RequestMethod.GET)
    public List<Movie> getAllMovies() throws SQLException {
        return database.getAllMovies();
    }

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public Movie getMovie(@RequestParam("movie_title") String title) throws SQLException {
        return database.getMovie(title);
    }

    @RequestMapping("/allactors")
    public List<Actor> getAllActors() throws SQLException {
        return database.getAllActors();
    }

    @RequestMapping("/actor")
    public Actor getActor() throws SQLException {
        return database.getActor("Tom Hanks", "1956-07-09");
    }

    @RequestMapping("/ratedbyall")
    public List<Movie> getRatedByAll() throws SQLException {
        return database.getRatedByAll();
    }

    // for testing purpose
    @RequestMapping("/allcomments")
    public List<Comments> getAllComments() throws SQLException {
        return database.getAllComments("Coming Home", "2014-05-16");
    }
}
