package Query;


import Tables.Actor;
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
    private Users user;

    public Retrieve(){
        this.database = new Database();
    }


    @RequestMapping(value = "/movie/{title}/{releasedate}", method = RequestMethod.GET)
    public Movie getMovie(@PathVariable("title") String title,
                          @PathVariable("releasedate") String releasedate) throws SQLException {
        return database.getMovie(title, releasedate);
    }

    @RequestMapping(value = "/allmovies", method = RequestMethod.GET)
    public List<Movie> getAllMovies() throws SQLException {
        return database.getAllMovies();
    }

    @RequestMapping(value = "/allmoviesone/{var1}", method = RequestMethod.GET)
    public List<Movie> getAllMovies(@PathVariable("var1") int var1) throws SQLException {
        return database.getAllMovies(var1);

    }

    @RequestMapping(value = "/allmoviestwo/{var1}/{var2}", method = RequestMethod.GET)
    public List<Movie> getAllMovies(@PathVariable("var1") int var1,
                                    @PathVariable("var2") int var2) throws SQLException {
        return database.getAllMovies(var1, var2);
    }

    @RequestMapping(value = "/actor/{name}/{birthday}", method = RequestMethod.GET)
    public Actor getActor(@PathVariable(value="name") String name,
                          @PathVariable(value="birthday") String birthday) throws SQLException {
        return database.getActor(name, birthday);
    }

    @RequestMapping(value = "/allactors", method = RequestMethod.GET)
    public List<Actor> getAllActors() throws SQLException {
        return database.getAllActors();
    }

    // simple aggregation
    @RequestMapping(value = "/moviecount/{name}/{birthday}", method = RequestMethod.GET)
    public MovieCount getMovieCount(@PathVariable(value="name") String name,
                                    @PathVariable(value="birthday") String birthday) throws SQLException {
        return database.getMovieCount(name, birthday);
    }

    // simple aggregation ALL
    // var = avg, sum, max, min, count
    @RequestMapping(value = "/rating/{title}/{releasedate}/{var}", method = RequestMethod.GET)
    public Rating getRatingAggregation(@PathVariable(value="title") String title,
                                     @PathVariable(value="releasedate") String releasedate,
                                     @PathVariable(value="var") String var) throws SQLException {
        return database.getRatingAggregation(var, title, releasedate);
    }

    // nested aggregation 1
    @RequestMapping(value = "/mostmovies", method = RequestMethod.GET)
    public List<Actor> getActorWithMostMovies() throws SQLException {
        return database.getActorWithMostMovies();
    }

    // nested aggregation 2
    @RequestMapping(value = "/leastmovies", method = RequestMethod.GET)
    public List<Actor> getActorWithLeastMovies() throws SQLException {
        return database.getActorWithLeastMovies();
    }

    // nested aggregation ALL
    @RequestMapping(value = "/nested/{var1}/{var2}", method = RequestMethod.GET)
    public List<Rating> getNestedAggregation(@PathVariable(value="var1") String var1,
                                       @PathVariable(value="var2") String var2) throws SQLException {
        return database.nestedRating(var1, var2);
    }

    // posting a review
    @RequestMapping(value = "/postreview", method = RequestMethod.POST)
    public int insertReview(@RequestBody LinkedHashMap<String, Object> object) throws SQLException {
        String text = (String) object.get("text");
        int rating = (int) object.get("rating");
        //String user = (String) object.get("accountName");
        String user = database.getUser();
        String title = (String) object.get("title");
        String date = (String) object.get("releaseDate");
        try{
            database.postReview(text, rating, user, title, date);
            return 1;
        } catch (SQLException e) {
            return 0;
        }

    }

    @RequestMapping(value = "/getreview/{id}/{user}", method = RequestMethod.GET)
    public Review getUserReview(@PathVariable(value="id") String id, @PathVariable(value="user") String user) throws SQLException {

        int foo = Integer.parseInt(id);
        //Review r = database.getUserReview(foo);

        if (database.getUser().equals(user)) {
            return database.getUserReview(foo);
        }
        else return null;
        //return database.getUserReview(foo);
    }

    @RequestMapping(value = "/updatereview", method = RequestMethod.POST)
    public int updateReview(@RequestBody LinkedHashMap<String, Object> object){
        String text = (String) object.get("text");
        int rating = (int) object.get("rating");
        int id = (int) object.get("id");
        try{
            database.updateReview(text, rating, id);
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    // deleting a movie
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/deletemovie/{title}/{releasedate}", method = RequestMethod.DELETE)
    public int deleteMovie(@PathVariable(value="title") String title,
                            @PathVariable(value="releasedate") String releasedate) throws SQLException {

        try {
            Movie m = database.getMovie(title, releasedate);
        } catch (SQLException e) {
            return 2;
        }
        try {
            database.deleteMovie(title, releasedate);
            return 1;
        } catch (SQLException e) {
            return 0;
        }
    }

    @RequestMapping("/ratedbyall")
    public List<Movie> getRatedByAll() throws SQLException {
        return database.getRatedByAll();
    }

    // DIVISION WITH CHOICES OF RATING
    @RequestMapping(value = "/ratedbyall2/{rating}", method = RequestMethod.GET)
    public List<Movie> getRatedByAllInput(@PathVariable(value="rating") int rating) throws SQLException {
        return database.getRatedByAllInput(rating);
    }

    // for testing purpose
    @RequestMapping("/allcomments")
    public List<Comments> getAllComments() throws SQLException {
        return database.getAllComments("Coming Home", "2014-05-16");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody LinkedHashMap<String, String> params) throws SQLException {
        String username = params.get("username");
        String password = params.get("password");

        return database.checkPassword(username, password);
    }

    @RequestMapping(value = "/logout")
    public void logout() throws SQLException {
        System.out.println("Logging out user");
        database.resetUser();
    }

    @RequestMapping(value = "/userExist", method = RequestMethod.GET)
    public String checkExist() throws SQLException {
        return database.getUser();
    }

    @RequestMapping(value = "/adminExist", method = RequestMethod.GET)
    public boolean checkAdminExist() throws SQLException {
        return database.getUser().equals("admin");
    }

    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public boolean adminlogin(@RequestBody LinkedHashMap<String, String> params) throws SQLException {
        String username = params.get("username");
        String password = params.get("password");

        return database.checkAdminPassword(username, password);
    }
}
