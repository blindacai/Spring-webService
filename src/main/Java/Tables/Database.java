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

        String query = "select * from (select a.name, a.birthday, a.nationality, count(t.title) as MovieCount" +
                        "from actor a, acts_in t where a.name = t.name and a.birthday = t.birthday + " +
                        "group by a.name, a.birthday) temp" +
                        "where temp.name = " + Query.formatVar(name) + " " +
                        "and temp.birthday = " + Query.formatVar(birthday);

        ResultSet result = getResult(query);
        Actor singleActor = new Actor(result);
        singleActor.setMovieCount(result.getInt("MovieCount"));
        result.next();
        return singleActor;
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

    public void postReview(String text, int rating, String user, String movieTitle, String releaseDate) {

        java.util.Date date = new java.util.Date();
        String modifiedDate = new SimpleDateFormat("yyyy-mm-dd").format(date);
        String query = "insert into review values (" + "'" + text + "', '" + modifiedDate + "', " + rating  + ", seqR.NEXTVAL, '" + user + "', '" + movieTitle + "', '" + releaseDate +"')";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Error: could not insert value int table.");
            e.printStackTrace();
        }
    }

}
