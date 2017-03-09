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
    }
}
