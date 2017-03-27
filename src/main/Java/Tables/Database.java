package Tables;

import Utils.Query;
import Utils.dbConnect;

import javax.xml.transform.Result;
import java.sql.*;
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

    public Movie getMovie(String title, String releasedate) throws SQLException {
        String query = "select * from movie where title = " + Query.formatVar(title) +
                " and releasedate = " + Query.formatVar(releasedate);
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
        String query = "select title, releasedate from movie";
        ResultSet results = getResult(query);
        //ResultSet results = getResult(Query.selectALL("movie"));
        while(results.next()){
            movies.add(new Movie(results));
        }
        System.out.println("hahaha");
        return movies;
    }

    public String getMovieConditions(int var){
        String userinput = "";

        switch (var) {
            case 1:
                userinput = "director";
                break;
            case 2:
                userinput = "distributedcompany";
                break;
            case 3:
                userinput = "director, distributedcompany";
                break;
            case 4:
                userinput = "2%";
                break;
            case 5:
                userinput = "1%";
                break;
            case 6:
                userinput = "%";
                break;
        }
        return userinput;
    }

    public List<Movie> getAllMovies(int var1) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String userinput = getMovieConditions(var1);
        String query = "";

        if (var1 <= 3) {
            query = "select title, releasedate, " + userinput + " from movie";
        }
        else query = "select * from movie where releasedate like " + Query.formatVar(userinput);

        System.out.println(var1);
        System.out.println(userinput);
        System.out.println(query);

        ResultSet results = getResult(query);
        while(results.next()){
            movies.add(new Movie(results));
        }
        return movies;
    }

    public List<Movie> getAllMovies(int var1, int var2) throws SQLException {
        String attributes = getMovieConditions(var1);
        String conditions = getMovieConditions(var2);
        String query = "select title, releasedate, " + attributes +
                " from movie where releasedate like " + Query.formatVar(conditions);

        List<Movie> movies = new ArrayList<Movie>();
        ResultSet results = getResult(query);
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

    // Simple Aggregation
    public MovieCount getMovieCount(String actor, String birthday) throws SQLException {
        String query = "select count(title) as moviecount from acts_in a where a.name = '" + actor +
                "' and a.birthday = '" + birthday + "'";
        ResultSet result = getResult(query);
        result.next();

        return new MovieCount(result);
    }


//    // Update Query: insert a new review
//    public void postReview(String text, int rating, String user, String movieTitle, String releaseDate) throws SQLException{
//
//        java.util.Date date = new java.util.Date();
//        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        String query = "insert into review values (" + "'" + text + "', '" + modifiedDate + "', " + rating  +
//                ", seqR.NEXTVAL, '" + user + "', '" + movieTitle + "', '" + releaseDate +"')";
//        statement.executeUpdate(query);
//        connection.commit();
//    }

    // Update Query: insert a new review
    public void postReview(String text, int rating, String user, String movieTitle, String releaseDate) throws SQLException{

        java.util.Date date = new java.util.Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        PreparedStatement ps = connection.prepareStatement("insert into review (text, postdate, rating, id, accountname, title, releasedate) " +
                "VALUES (?, ?, ?, seqR.NEXTVAL, ?, ?, ?)");
        ps.setString(1, text);
        ps.setString(2, modifiedDate);
        ps.setInt(3, rating);
        ps.setString(4, user);
        ps.setString(5, movieTitle);
        ps.setString(6, releaseDate);
        ps.executeUpdate();
        connection.commit();
    }

    // Delete Query: delete a movie
    public void deleteMovie(String title, String releasedate) throws SQLException {
        String query = "delete from movie where title ='" + title + "' and releasedate = '" + releasedate + "'";
        statement.executeUpdate(query);
        connection.commit();
    }

    // Nested Aggregation1: get the actor who has acted in the most movies
    public List<Actor> getActorWithMostMovies() throws SQLException {

        List<Actor> actors = new ArrayList<Actor>();

        String query = "SELECT temp.name, temp.birthday, temp.nationality " +
                "FROM (SELECT a.name, a.birthday, a.nationality, count(i.title) AS mcount " +
                "FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday, a.nationality) temp " +
                "WHERE temp.mcount >= all (SELECT count(i.title) FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday)";
        ResultSet results = getResult(query);

        while(results.next()){
            actors.add(new Actor(results));
        }
        return actors;
    }

    // Nested Aggregation2: get the actor who has acted in the least movies
    public List<Actor> getActorWithLeastMovies() throws SQLException {

        List<Actor> actors = new ArrayList<Actor>();

        String query = "SELECT temp.name, temp.birthday, temp.nationality " +
                "FROM (SELECT a.name, a.birthday, a.nationality, count(i.title) AS mcount " +
                "FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday, a.nationality) temp " +
                "WHERE temp.mcount <= all (SELECT count(i.title) FROM actor a, acts_in i " +
                "where a.name = i.name and a.birthday = i.birthday " +
                "GROUP BY a.name, a.birthday)";
        ResultSet results = getResult(query);

        while(results.next()){
            actors.add(new Actor(results));
        }
        return actors;
    }

}
