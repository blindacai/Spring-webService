package Tables;

import Utils.Query;
import Utils.dbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by linda on 3/9/2017.
 */
public class Database extends dbConnect {
    private Connection connection;
    private Statement statement;

    public Database(){
        this.connect();
        this.connection = this.getConnection();
    }

    public Statement getStatement() throws SQLException {
        this.statement = connection.createStatement();
        return this.statement;
    }

    public ResultSet getResult(String query) throws SQLException {
        return this.getStatement().executeQuery(query);
    }

    public Movie getMovie(String title) throws SQLException {
        String query = "select * from movie where title = " + Query.formatVar(title);
        ResultSet result = getResult(query);
        result.next();
        return new Movie(result);
    }

    public Actor getActor(String name, String birthday) throws SQLException {
        String query = "select * from actor " +
                       "where name = " + Query.formatVar(name) + " " +
                       "and birthday = " + Query.formatVar(birthday);
        ResultSet result = getResult(query);
        result.next();
        return new Actor(result);
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        ResultSet results = getResult(Query.selectALL("movie"));
        while(results.next()){
            movies.add(new Movie(results));
        }
        return movies;
    }
}
