package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Actor {
    private String name;
    private String birthday;
    private String nationality;

    public Actor(ResultSet result) throws SQLException {
        this.name = result.getString("name");
        this.birthday = result.getString("birthday");
        this.nationality = result.getString("nationality");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
