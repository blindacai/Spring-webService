package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {

    private String accountName = null;
    private String password = null;

    public Users(ResultSet result){
        try{
            this.accountName = result.getString("accountname");
            this.password = result.getString("password");
        }catch (SQLException e){
            // do nothing
        }
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "Username: " + getAccountName() + " Password: " + getPassword();
    }
}
