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
        Movie movie = new Movie(result, this, true);
        return movie;
    }

    public Actor getActor(String name, String birthday) throws SQLException {
        String query = "select * from actor " +
                "where name = " + Query.formatVar(name) + " " +
                "and birthday = " + Query.formatVar(birthday);
        ResultSet result = getResult(query);
        result.next();
        return new Actor(result);
    }

    public Review getReview(int id) throws SQLException {
        String query = "select * from review " +
                       "where id = " + id;
        ResultSet result = getResult(query);
        result.next();
        return new Review(result);
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        String query = "select title, releasedate from movie";
        ResultSet results = getResult(query);
        while(results.next()){
            movies.add(new Movie(results, this, false));
        }
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
        else query = "select title, releasedate from movie where releasedate like " + Query.formatVar(userinput);

        System.out.println(var1);
        System.out.println(userinput);
        System.out.println(query);

        ResultSet results = getResult(query);
        while(results.next()){
            movies.add(new Movie(results, this, false));
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
            movies.add(new Movie(results, this, false));
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

    public List<Comments> getAllComments(String title, String releasedate) throws SQLException {
        List<Comments> comments = new ArrayList<Comments>();
        String query = "select * from comments " +
                       "where title = " + Query.formatVar(title) + " " +
                       "and releasedate = " + Query.formatVar(releasedate);
        ResultSet results = getResult(query);
        while(results.next()){
            comments.add(new Comments(results));
        }
        return comments;
    }

    public List<Review> getAllReviews(String title, String releasedate) throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
        String query = "select * from review " +
                       "where title = " + Query.formatVar(title) + " " +
                       "and releasedate = " + Query.formatVar(releasedate);
        ResultSet results = getResult(query);
        while(results.next()){
            reviews.add(new Review(results));
        }
        return reviews;
    }

    public List<Movie> getRatedByAll() throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        String query =  "select m.title, m.releasedate from movie m " +
                        "where not exists((select u1.accountname from users u1) " +
                        "minus (select u2.accountname from users u2, review r " +
                        "where r.rating = 5 and u2.accountname = r.accountname and r.title = m.title and r.releasedate = m.releasedate))";
        ResultSet results = getResult(query);

        while(results.next()){
            movies.add(new Movie(results, this, false));
        }
        return movies;
    }

    // DIVISION WITH CHOICES OF RATING
    public List<Movie> getRatedByAllInput(int rating) throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        String query =  "select m.title, m.releasedate from movie m " +
                "where not exists((select u1.accountname from users u1) " +
                "minus (select u2.accountname from users u2, review r " +
                "where r.rating = " + rating +" and u2.accountname = r.accountname and r.title = m.title and r.releasedate = m.releasedate))";
        ResultSet results = getResult(query);

        while(results.next()){
            movies.add(new Movie(results, this, false));
        }
        return movies;
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

    // Simple Aggregation All
    public Rating getRatingAggregation(String var, String title, String releasedate) throws SQLException {
        String query = "select " + var + "(rating) as rating from movie m, review r where m.title = r.title and " +
                "m.releasedate = r.releasedate and m.title = '" + title + "' and m.releasedate = '" + releasedate + "'";
        ResultSet result = getResult(query);
        result.next();

        return new Rating(result);
    }

    // return a review object
    public Review getUserReview(int id) throws SQLException{
        String query = "select * from review where id = " + id;
        ResultSet result = getResult(query);
        result.next();

        return new Review(result);
    }

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

    // Update Query2: update a review
    public void updateReview(String text, int rating, int id) throws SQLException {
        java.util.Date date = new java.util.Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        PreparedStatement ps = connection.prepareStatement("update review set text = ?, rating = ?, postdate = ? where id = ?");
        ps.setString(1, text);
        ps.setInt(2, rating);
        ps.setString(3, modifiedDate);
        ps.setInt(4, id);
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

    // Nested Aggregation ALL
    public List<Rating> nestedRating(String var1, String var2) throws SQLException {

        List<Rating> ratings = new ArrayList<>();
        String query;

        if (var2.equals("min")) {
            query = "SELECT temp.title as title, temp.releasedate as releasedate, temp.rating as rating " +
                    "FROM (SELECT m.title, m.releasedate, " + var1 + "(rating) AS rating " +
                    "FROM movie m, review r " +
                    "where m.title = r.title and m.releasedate = r.releasedate " +
                    "GROUP BY m.title, m.releasedate) temp " +
                    "WHERE temp.rating <= all (SELECT " + var1 + "(rating) AS rating FROM movie m, review r " +
                    "where m.title = r.title and m.releasedate = r.releasedate " +
                    "GROUP BY m.title, m.releasedate)";

            ResultSet result = getResult(query);
            while(result.next()) {
                ratings.add(new Rating(result));
            }
            System.out.println(query);
            return ratings;
        }
        else if (var2.equals("max")) {

            query = "SELECT temp.title as title, temp.releasedate as releasedate, temp.rating as rating " +
                    "FROM (SELECT m.title, m.releasedate, " + var1 + "(rating) AS rating " +
                    "FROM movie m, review r " +
                    "where m.title = r.title and m.releasedate = r.releasedate " +
                    "GROUP BY m.title, m.releasedate) temp " +
                    "WHERE temp.rating >= all (SELECT " + var1 + "(rating) AS rating FROM movie m, review r " +
                    "where m.title = r.title and m.releasedate = r.releasedate " +
                    "GROUP BY m.title, m.releasedate)";

            ResultSet result = getResult(query);
            while(result.next()) {
                ratings.add(new Rating(result));
            }
            System.out.println(query);
            return ratings;
        }

        else {
            query = "SELECT " + var2 + "(temp.rating) as rating " +
                    "FROM (SELECT m.title, m.releasedate, " + var1 + "(rating) AS rating " +
                    "FROM movie m, review r " +
                    "where m.title = r.title and m.releasedate = r.releasedate " +
                    "GROUP BY m.title, m.releasedate) temp";

            ResultSet result = getResult(query);
            while(result.next()) {
                ratings.add(new Rating(result));
            }
            System.out.println(query);
            return ratings;
        }
    }

}
