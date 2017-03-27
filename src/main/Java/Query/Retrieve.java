package Query;


import Tables.Actor;
import Tables.Database;
import Tables.Movie;
import Tables.MovieCount;
import org.springframework.web.bind.annotation.*;

import Tables.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.sql.SQLException;
import java.util.LinkedHashMap;
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
    @RequestMapping(value = "/movie/{title}/{releasedate}", method = RequestMethod.GET)
    public Movie getMovie(@PathVariable("title") String title,
                          @PathVariable("releasedate") String releasedate) throws SQLException {
        return database.getMovie(title, releasedate);
    }

    @RequestMapping(value = "/allmovies", method = RequestMethod.GET)
    public List<Movie> getAllMovies() throws SQLException {
        return database.getAllMovies();
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/allmovies1/{var1}", method = RequestMethod.GET)
    public List<Movie> getAllMovies(@PathVariable("var1") int var1) throws SQLException {
        return database.getAllMovies(var1);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/allmovies2/{var1}/{var2}", method = RequestMethod.GET)
    public List<Movie> getAllMovies(@PathVariable("var1") int var1,
                                    @PathVariable("var2") int var2) throws SQLException {
        return database.getAllMovies(var1, var2);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/actor/{name}/{birthday}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable(value="name") String name,
                          @PathVariable(value="birthday") String birthday) throws SQLException {
        return database.getActor(name, birthday);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/allactors", method = RequestMethod.GET)
    public List<Actor> getAllActors() throws SQLException {
        return database.getAllActors();
    }

    // simple aggregation
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/moviecount/{name}/{birthday}", method = RequestMethod.GET)
    public MovieCount getMovieCount(@PathVariable(value="name") String name,
                                    @PathVariable(value="birthday") String birthday) throws SQLException {
        return database.getMovieCount(name, birthday);
    }

    // nested aggregation 1
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/mostmovies", method = RequestMethod.GET)
    public List<Actor> getActorWithMostMovies() throws SQLException {
        return database.getActorWithMostMovies();
    }

    // nested aggregation 2
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/leastmovies", method = RequestMethod.GET)
    public List<Actor> getActorWithLeastMovies() throws SQLException {
        return database.getActorWithLeastMovies();
    }

//    // posting a review
//    @CrossOrigin(origins = "http://localhost:3000")
//    @RequestMapping(value = "/postreview", method = RequestMethod.POST)
//    public void insertReview(@RequestParam(value="text") String text, @RequestParam(value="rating") int rating,
//                             @RequestParam(value="user") String user, @RequestParam(value="movietitle") String title,
//                             @RequestParam(value="releasedate") String releasedate) throws SQLException {
//        database.postReview(text, rating, user, title, releasedate);
//    }

    // posting a review
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/postreview", method = RequestMethod.POST)
    public void insertReview(@RequestBody LinkedHashMap<String, Object> object) throws SQLException {

        System.out.println("entered review");

        String text = (String) object.get("text");
        int rating = (int) object.get("rating");
        String user = (String) object.get("accountName");
        String title = (String) object.get("title");
        String date = (String) object.get("releaseDate");
        database.postReview(text, rating, user, title, date);

        System.out.println("posted review");
        //Review review = (Review) object;
        //database.postReview(review.getText(), review.getRating(), review.getAccountName(), review.getTitle(), review.getReleaseDate());
    }

    // deleting a movie
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/deletemovie", method = RequestMethod.DELETE)
    public void deleteMovie(@RequestParam(value="title") String title,
                            @RequestParam(value="releasedate") String releasedate) throws SQLException {
        database.deleteMovie(title, releasedate);
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
