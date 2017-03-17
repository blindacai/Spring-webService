package movie.Tables;

import movie.dbConnect;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public Users getUser(String name) throws SQLException {
        String query = "select * from users where accountname = " + "'" + name + "'";
        ResultSet result = getResult(query);
        result.next();
        return new Users(result);
    }

    public void postReview(String text, int rating, String user, String movieTitle, String releaseDate) {

        Date date = new Date();
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
