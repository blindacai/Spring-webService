package Tables;

import java.sql.SQLException;

/**
 * Created by linda on 3/8/2017.
 */
public class testMain {
    public static void main(String[] args) throws SQLException {
        Movie movie = new Movie("Coming Home");
        System.out.println(movie.getDirector());
        System.out.println(movie.getCompany());
    }
}
