package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class Users {

    private String accountName;
    private String email;
    private String birthday;

    public Users(ResultSet result) throws SQLException {
        this.accountName = result.getString("accountname");
        this.email = result.getString("email");
        this.birthday = result.getString("birthday");
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
