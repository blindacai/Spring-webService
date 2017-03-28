package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by csam on 2017-03-28.
 */
public class Rating {
    private double rating;

    public Rating(ResultSet result) throws SQLException {
        this.rating = result.getInt("rating");
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
