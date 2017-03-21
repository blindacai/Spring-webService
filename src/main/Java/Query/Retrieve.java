package Query;

import Tables.Actor;
import Tables.Database;
import Tables.Movie;
import Tables.MovieCount;
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

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/movie")
    public Movie getMovie() throws SQLException {
        return database.getMovie("Coming Home");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/allmovies")
    public List<Movie> getAllMovies() throws SQLException {
        return database.getAllMovies();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/actor")
    public Actor getActor(@RequestParam(value="name") String name, @RequestParam(value="birthday") String birthday) throws SQLException {
        return database.getActor(name, birthday);
        //return database.getActor("Tom Hanks", "1956-07-09");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/allactors")
    public List<Actor> getAllActors() throws SQLException {
        return database.getAllActors();
    }

    // simple aggregation
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/moviecount", method = RequestMethod.GET)
    public MovieCount getMovieCount(@RequestParam(value="name") String name, @RequestParam(value="birthday") String birthday) throws SQLException {
        return database.getMovieCount(name, birthday);
    }

    // nested aggregation
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/mostmovies", method = RequestMethod.GET)
    public Actor getActorWithMostMovies() throws SQLException {
        return database.getActorWithMostMovies();
    }

    // posting a review
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/postreview", method = RequestMethod.POST)
    public void insertReview(@RequestParam(value="text") String text, @RequestParam(value="rating") int rating, @RequestParam(value="user") String user, @RequestParam(value="movietitle") String title, @RequestParam(value="releasedate") String releasedate) {
        database.postReview(text, rating, user, title, releasedate);
    }

    // deleting a movie
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/deletemovie", method = RequestMethod.DELETE)
    public void deleteMovie(@RequestParam(value="title") String title, @RequestParam(value="releasedate") String releasedate) {
        database.deleteMovie(title, releasedate);
    }

}
