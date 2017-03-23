package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by csam on 2017-03-21.
 */
public class MovieCount {

    private int moviecount;

    public MovieCount(ResultSet result) throws SQLException {
        this.moviecount = result.getInt("moviecount");
    }

    public int getMoviecount() {
        return moviecount;
    }

    public void setMoviecount(int moviecount) {
        this.moviecount = moviecount;
    }
}
