package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;
import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService {
    private final Connection connection;

    public DBService() {
        this.connection = getH2Connection();
    }

    public Connection getConnection() {
        return connection;
    }

    //    public UsersDataSet getUser(long id) throws DBException {
//        try {
//            return (new UsersDAO(connection).get(id));
//        } catch (SQLException e) {
//            throw new DBException(e);
//        }
//    }

//    public long addUser(UsersDataSet usersDataSet) throws DBException {
//        return addUser(usersDataSet.getLogin(),
//                       usersDataSet.getEmail(),
//                       usersDataSet.getPassword());
//    }
//
//    public long addUser(String login, String email, String password) throws DBException {
//        try {
//            connection.setAutoCommit(false);
//            UsersDAO dao = new UsersDAO(connection);
//            dao.createTable();
//            dao.insertUser(login, email, password);
//            connection.commit();
//            return dao.getByLogin(login).getId();
//        } catch (SQLException e) {
//            try {
//                connection.rollback();
//            } catch (SQLException ignore) {
//            }
//            throw new DBException(e);
//        } finally {
//            try {
//                connection.setAutoCommit(true);
//            } catch (SQLException ignore) {
//            }
//        }
//    }

    public void cleanUp() throws DBException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Connection getMysqlConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();

            url.
                    append("jdbc:mysql://").        // db type
                    append("localhost:").           // host name
                    append("3306/").                // port
                    append("db_example?").          // db name
                    append("user=tutty&").          // login
                    append("password=tutty");       // password

            System.out.println("URL: " + url + "\n");

            return DriverManager.getConnection(url.toString());

        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getH2Connection() {
        try {
            String url = "jdbc:h2:./h2db;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE";
            String name = "tutty";
            String pass = "tutty";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

            return DriverManager.getConnection(url, name, pass);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
