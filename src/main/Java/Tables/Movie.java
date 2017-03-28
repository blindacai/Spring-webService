package Tables;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    private List<Comments> comments;
    private List<Review> reviews;
    private Database database;
    private Double rating;

    public Movie(ResultSet result, Database database, boolean singleMovie) throws SQLException {
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


        if(singleMovie){
            this.comments = database.getAllComments(title, released);
            this.reviews = database.getAllReviews(title, released);
        }
        

        try {
            this.rating = result.getDouble("rating");
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

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
