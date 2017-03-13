package movie.Tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Comments {

    private String text;
    private int upVotes;
    private String postdate;
    private int id;
    private String accountName;
    private String title;
    private String releaseDate;

    public Comments(ResultSet result) throws SQLException {
        this.text = result.getString("text");
        this.upVotes = result.getInt("upvotes");
        this.postdate = result.getString("postdate");
        this.id = result.getInt("id");
        this.accountName = result.getString("accountname");
        this.title = result.getString("title");
        this.releaseDate = result.getString("releasedate");

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }


}
