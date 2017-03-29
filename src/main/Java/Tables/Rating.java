package Tables;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * Created by csam on 2017-03-28.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rating {
    private double rating;
    private String title;
    private String releasedate;

    public Rating(ResultSet result) throws SQLException {

        try {
            this.title = result.getString("title");
        } catch (java.sql.SQLException e) {
            this.title = null;
        }
        try {
            this.releasedate = result.getString("releasedate");
        } catch (java.sql.SQLException e) {
            this.releasedate = null;
        }
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }
}
