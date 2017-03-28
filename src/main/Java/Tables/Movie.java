package Tables;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by linda on 3/8/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
    private String title;
    private String released;
    private String director;
    private String company;
    private String comments;
    private String reviews;
    private Database database;
    private Double rating;

    public Movie(ResultSet result, Database database) throws SQLException {
        this.title = result.getString("title");
        this.released = result.getString("releasedate");
        try {
            this.director = result.getString("director");
        } catch (java.sql.SQLException e) {
            this.director = null;
        }
        try {
            this.company = result.getString("distributedcompany");
        } catch (java.sql.SQLException e) {
            this.company = null;
        }
        this.database = database;

        try {
            double d = result.getDouble("rating");
            DecimalFormat f = new DecimalFormat("##.0");
            this.rating = Double.parseDouble(f.format(d));
        } catch (java.sql.SQLException e) {
            this.rating = null;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Comments> getComments() throws SQLException {
        return database.getAllComments(this.title, this.released);
    }

    public List<Review> getReviews() throws SQLException {
        return database.getAllReviews(this.title, this.released);
    }
}
