package Tables;

import Utils.dbConnect;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        return new Movie(this, title);
    }
}
