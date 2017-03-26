package Tables;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by linda on 3/8/2017.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {
    private String title;
    private String released;
    private String director;
    private String company;

    public Movie(ResultSet result) throws SQLException {
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
//        this.director = result.getString("director");
//        this.company = result.getString("distributedcompany");
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
}
