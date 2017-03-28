package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by csam on 2017-03-28.
 */
public class Rating {
    private int rating;

    public Rating(ResultSet result) throws SQLException {
        this.rating = result.getInt("rating");
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
