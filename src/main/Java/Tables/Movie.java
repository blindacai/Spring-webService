package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by linda on 3/8/2017.
 */
public class Movie {
    private String title;
    private String released;
    private String director;
    private String company;
    private String comments;
    private Database database;

    public Movie(ResultSet result) throws SQLException {
        this.title = result.getString("title");
        this.released = result.getDate("releasedate").toString();
        this.director = result.getString("director");
        this.company = result.getString("distributedcompany");

        database = new Database();
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
}
