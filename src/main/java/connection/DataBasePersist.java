package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBasePersist {

    private static final String URL = "jdbc:postgresql://localhost:5440/contracts_db";
    private static final String USER = "postgres";
    private static final String PASS = "123456";

    private Connection connection;

    public DataBasePersist() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASS);
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }
}
