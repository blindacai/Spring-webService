package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import static jdk.nashorn.internal.objects.NativeMath.round;

/**
 * Created by csam on 2017-03-28.
 */
public class Rating {
    private double rating;

    public Rating(ResultSet result) throws SQLException {
        double d = result.getDouble("rating");
        DecimalFormat f = new DecimalFormat("##.0");
        this.rating = Double.parseDouble(f.format(d));
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
