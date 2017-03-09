package Tables;

import Query.dbConnect;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by linda on 3/8/2017.
 */
public class Movie {
    private String title;
    private Data released;
    private String director;
    private String company;

    public Movie(String title) throws SQLException {
        this.title = title;
        dbConnect con = new dbConnect();
        con.connect();
        Statement stat = con.getConnection().createStatement();
        String query = "select * from movie where title = " + "'" + title + "'";
        ResultSet result = stat.executeQuery(query);
        result.next();
        this.director = result.getString("director");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data getReleased() {
        return released;
    }

    public void setReleased(Data released) {
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
