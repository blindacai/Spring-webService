package movie.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Upvote {

    private String accountName;
    private int id;

    public Upvote(ResultSet result) throws SQLException {
        this.accountName = result.getString("accountname");
        this.id = result.getInt("id");
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
