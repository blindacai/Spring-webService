package Tables;

import java.sql.SQLException;

/**
 * Created by linda on 3/8/2017.
 */
public class TestMain {
    public static void main(String[] args) throws SQLException {
        Database database = new Database();


        Movie movie = database.getMovie("Coming Home");
        System.out.println(movie.getDirector());
        System.out.println(movie.getCompany());

        Users user = database.getUser("foobar");
        System.out.println(user.getAccountName());
        System.out.println(user.getEmail());
        System.out.println(user.getBirthday());

        //database.postReview("Good", 1, "foobar", "Harry Potter", "2001-11-04");
        //database.deleteMovie("Harry Potter", "2001-11-04");

        Actor actor = database.getActorWithMostMovies();
        System.out.println(actor.getName());
        System.out.println(actor.getBirthday());
        System.out.println(actor.getNationality());
    }
}
