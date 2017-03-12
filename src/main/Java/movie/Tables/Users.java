package movie.Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Users {

    private String accountName;
    private String email;
    private Date birthday;

    public Users(ResultSet result) throws SQLException {
        this.accountName = result.getString("accountname");
        this.email = result.getString("email");
        this.birthday = result.getDate("birthday");
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
