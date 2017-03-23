package Query;

import Tables.*;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.springframework.web.bind.annotation.*;

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


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/movie/{title}/{releasedate}", method = RequestMethod.GET)
    public Movie getMovie(@PathVariable("title") String title, @PathVariable("releasedate") String releasedate) throws SQLException {
        System.out.println(title);
        System.out.println(releasedate);
        // able to pass title and releasedate to backend, but need to fix getMoive method

        return database.getMovie(title);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/actor/{name}/{birthday}")
    public Actor getActor(@PathVariable("name") String name, @PathVariable("birthday") String birthday) throws SQLException {
        System.out.println(name);
        System.out.println(birthday);
        return database.getActor(name, birthday);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/allactors")
    public List<Actor> getAllActors() throws SQLException {
        return database.getAllActors();
    }

    // simple aggregation
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/moviecount/{name}/{birthday}", method = RequestMethod.GET)
    public MovieCount getMovieCount(@PathVariable("name") String name, @PathVariable("birthday") String birthday) throws SQLException {
        System.out.println("We are in moviecount");
        return database.getMovieCount(name,birthday);
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
