package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Review {

    private String text;
    private String postdate;
    private int id;
    private int rating;
    private String accountName;
    private String title;
    private String releaseDate;

    public Review(ResultSet result) throws SQLException {
        this.text = result.getString("text");
        this.postdate = result.getString("postdate");
        this.id = result.getInt("id");
        this.rating = result.getInt("rating");
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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

