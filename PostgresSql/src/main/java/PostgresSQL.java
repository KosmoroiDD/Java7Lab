import java.sql.*;
import java.util.Properties;

//import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;
// network.*;

//import static CollectionObjects.Collectionss.stringCollection;

public class PostgresSQL {

    // Database connection settings
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
    private static final String DB_USER = "s408010";
    private static final String DB_PASSWORD = "ffDl}0851";
    private static Connection connection;
    private static final PGSimpleDataSource ds = new PGSimpleDataSource();

    public static void main(String[] args) {
        initializeDatabaseConnection();
    }


    public static void initializeDatabaseConnection() {
        System.out.println("Testing PostgreSQL JDBC connection...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC Driver not found!");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver registered!");

        try {
            Properties props = new Properties();
            props.setProperty("user", DB_USER);
            props.setProperty("password", DB_PASSWORD);
            props.setProperty("ssl", "false");

            connection = DriverManager.getConnection(DB_URL, props);

            if (connection != null) {
                System.out.println("Successfully connected to PostgreSQL database!");
                createTablesIfNotExists();

                /*Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT version()");
                if (rs.next()) {
                    System.out.println("PostgreSQL version: " + rs.getString(1));
                }*/
            } else {
                System.err.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createTablesIfNotExists() throws SQLException {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(100) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        String createCollectionTable = """
            CREATE TABLE IF NOT EXISTS collection_items (
                id SERIAL PRIMARY KEY,
                owner_id INTEGER REFERENCES users(id),
                data JSONB NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createCollectionTable);
        }
    }

    // ... [rest of the code remains the same] ...
}