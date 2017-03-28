package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by csam on 2017-03-28.
 */
public class Rating {
    private double rating;

    public Rating(ResultSet result) throws SQLException {
        this.rating = round(result.getDouble("rating"), 1);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
