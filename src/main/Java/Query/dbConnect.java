package Query;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnect {
    private Connection connection;

    public dbConnect(){

    }

    public void connect() {
        connection = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver needs to be included in the Java library path");
            e.printStackTrace();
        }
        System.out.println("Oracle JDBC Driver Registered");

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:ug",
                                                     "ora_t6z9a",
                                                     "a25834102");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Connection Failed. Check output console");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
