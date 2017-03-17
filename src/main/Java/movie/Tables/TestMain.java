package movie.Tables;


import java.sql.Date;
import java.sql.SQLException;

public class TestMain {
    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        Users user = database.getUser("foobar");
        System.out.println(user.getAccountName());
        System.out.println(user.getEmail());
        System.out.println(user.getBirthday());

        database.postReview("Good", 1, "foobar", "Harry Potter", "2001-11-04");
    }
}