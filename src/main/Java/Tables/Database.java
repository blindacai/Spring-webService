package Tables;

import Utils.Query;
import Utils.dbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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

    public List<Actor> getAllActors() throws SQLException {
        List<Actor> actors = new ArrayList<Actor>();
        ResultSet results = getResult(Query.selectALL("actor"));
        while(results.next()){
            actors.add(new Actor(results));
        }
        return actors;
    }


    public Users getUser(String name) throws SQLException {
        String query = "select * from users where accountname = " + "'" + name + "'";
        ResultSet result = getResult(query);
        result.next();
        return new Users(result);
    }


    // Update Query: insert a new review
    public void postReview(String text, int rating, String user, String movieTitle, String releaseDate) {

        java.util.Date date = new java.util.Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String query = "insert into review values (" + "'" + text + "', '" + modifiedDate + "', " + rating  + ", seqR.NEXTVAL, '" + user + "', '" + movieTitle + "', '" + releaseDate +"')";
        try {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error: could not insert value into table.");
            e.printStackTrace();
        }
    }

    // Delete Query: delete a movie
    public void deleteMovie(String title, String releasedate) {
        String query = "delete from movie where title ='" + title + "' and releasedate = '" + releasedate + "'";
        try {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException e) {
            System.err.println("Error: could not remove value from table.");
            e.printStackTrace();
        }
    }

    // Nested Aggregation: get the actor who has acted in the most movies
    public Actor getActorWithMostMovies() throws SQLException {

        String query = "SELECT temp.name, temp.birthday, temp.nationality, temp.mcount " +
                "FROM (SELECT a.name, a.birthday, a.nationality, count(i.title) AS mcount " +
                "FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday, a.nationality) temp " +
                "WHERE temp.mcount >= all (SELECT count(i.title) FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday)";
        ResultSet result = getResult(query);
        result.next();

        return new Actor(result);

    }

}
