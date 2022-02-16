package dbService.dao;

import dbService.DBException;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class UsersDAO {

    private final Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet getById(long id) throws SQLException {
        return executor.execQuery("SELECT * FROM users WHERE id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2),
                    result.getString(3), result.getString(4));
        });
    }

    public UsersDataSet getByLogin(String login) throws SQLException {
        try {
            return executor.execQuery("SELECT * FROM users WHERE login='" + login + "'", result -> {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2),
                        result.getString(3), result.getString(4));
            });
        } catch (SQLException e) {
            if (e.getErrorCode() == 2000)
                return null;
            throw e;
        }
    }

    public void insertUser(UsersDataSet usersDataSet) throws SQLException {
        executor.execUpdate("INSERT INTO users (login, email, password) VALUES ('" +
                usersDataSet.getLogin()    + "', '" +
                usersDataSet.getEmail()    + "', '" +
                usersDataSet.getPassword() + "')");
    }

    public void insertUser(String login, String email, String password) throws SQLException {
        insertUser(new UsersDataSet(login, email, password));
    }

    public void createTable() throws SQLException {
        executor.execUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT AUTO_INCREMENT, login VARCHAR(256) UNIQUE, " +
                "email VARCHAR(256), " +
                "password VARCHAR(256), " +
                "PRIMARY KEY (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("DROP TABLE users");
    }
}
