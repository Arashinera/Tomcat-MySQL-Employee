package app.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConn {

    private static final Logger LOGGER = Logger.getLogger(DBConn.class.getName());

    public static Connection connect() {

        Properties props = new Properties();
        Connection connection = null;

        try {
            props.load(DBConn.class.getResourceAsStream("/db/db.properties"));
            Class.forName(props.getProperty("dbDriver"));
            connection = DriverManager.getConnection(
                    props.getProperty("dbUrl"),
                    props.getProperty("dbUser"),
                    props.getProperty("dbPass"));
        } catch (SQLException | IOException | ClassNotFoundException exception) {
            LOGGER.log(Level.WARNING, exception.getMessage(), exception);
        }

        return connection;
    }
}
