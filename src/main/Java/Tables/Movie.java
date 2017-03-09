package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by linda on 3/8/2017.
 */
public class Movie {
    private String title;
    private String released;
    private String director;
    private String company;

    public Movie(Database database, String title) throws SQLException {
        this.title = title;
        String query = "select * from movie where title = " + "'" + title + "'";
        ResultSet result = database.getResult(query);
        result.next();
        this.director = result.getString("director");
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
