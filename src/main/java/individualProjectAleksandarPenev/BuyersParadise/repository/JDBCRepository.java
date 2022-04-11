package individualProjectAleksandarPenev.BuyersParadise.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCRepository {

    @Autowired
    private Environment env;

    protected Connection getDatabaseConnection() {

        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            return connection;
        }
        catch (SQLException e) {
            throw new IllegalStateException("JDBC driver failed to connect to the database " + url + ".", e);
        }
    }
}
