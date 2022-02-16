package accounts;

import dbService.DBException;
import dbService.DBService;
import dbService.dao.UsersDAO;
import dbService.dataSets.UsersDataSet;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for storing account information.
 */
public class AccountService {
    DBService dbService;
    private final Map<String, UsersDataSet> sessionIdToProfile;

    /**
     * Constructor.
     */
    public AccountService(DBService dbService) {
        this.dbService = dbService;
        sessionIdToProfile = new HashMap<>();
    }

    /**
     * Adds a new user.
     * @param usersDataSet User profile that is being added.
     */
    public void addNewUser(UsersDataSet usersDataSet) throws SQLException {

        Connection connection = dbService.getConnection();

        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(usersDataSet);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new SQLException(e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Returns the user profile by login.
     * @param login user login
     * @return user profile by login
     */
    public UsersDataSet getUserByLogin(String login) throws SQLException {

        Connection connection = dbService.getConnection();

        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        return (new UsersDAO(dbService.getConnection()).getByLogin(login));
    }

    /**
     * Returns the user profile by session ID.
     * @param sessionId user session ID
     * @return user profile by session ID
     */
    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    /**
     * Adds a new session.
     * @param sessionId new session ID
     * @param usersDataSet profile of the user who owns the session
     */
    public void addSession(String sessionId, UsersDataSet usersDataSet) {
        sessionIdToProfile.put(sessionId, usersDataSet);
    }

    /**
     * Removes a session by id.
     * @param sessionId session id to delete
     */
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
