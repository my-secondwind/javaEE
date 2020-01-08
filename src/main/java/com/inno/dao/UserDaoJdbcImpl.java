package com.inno.dao;

import com.inno.ConnectionManager.ConnectionManager;
import com.inno.ConnectionManager.ConnectionManagerJdbcImpl;
import com.inno.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EJB
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbcImpl.class);
    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();
    public static final String INSERT_USER_STATEMENT = "INSERT INTO users values (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_USER_BY_ID_STATEMENT = "SELECT * FROM users WHERE id = ?";
    public static final String SELECT_USER_BY_EMAIL_PASSWORD_STATEMENT = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String UPDATE_USER_STATEMENT = "UPDATE users SET name = ?, birthday = ?, login_id = ?, " +
            "city = ?, email = ?, description = ?, password = ? WHERE id = ?";
    public static final String DELETE_USER_STATEMENT = "DELETE FROM users WHERE id = ?";
    public static final String SELECT_ALL_FROM_USERS = "SELECT * FROM users";

    /**
     * Add User into DB.
     *
     * @param user that is added to DB.
     * @return {@code true} if user was added successfully.
     */

    @Override
    public boolean addUser(User user) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_STATEMENT)) {
            LOGGER.debug("Adding new user to DB");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setString(7, user.getPassword());
            result = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException e) {
            LOGGER.error("Error during adding user {}", user, e);
        }
        return result;
    }

    /**
     * Get user from DB by ID.
     *
     * @param id of user to be found.
     * @return user that created based pn info from DB
     */
    @Override
    public User getUserById(Integer id) {
        User user = new User();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_STATEMENT)) {
            LOGGER.debug("Getting user by Id from DB");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                createUserObject(user, resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during getting object by Id {}", id, e);
        }
        return user;
    }

    /**
     * Set object's fields according to DB info.
     *
     * @param user      object to be filled.
     * @param resultSet with info about object.
     * @throws SQLException - if any SQL errors occurs.
     */
    private void createUserObject(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setBirthday(resultSet.getString(3));
        user.setLoginId(resultSet.getInt(4));
        user.setCity(resultSet.getString(5));
        user.setEmail(resultSet.getString(6));
        user.setDescription(resultSet.getString(7));
        user.setPassword(resultSet.getString(8));
    }

    /**
     * Update DB row for the following user.
     *
     * @param user updated user.
     * @return {@code true} if user was updated successfully.
     */
    @Override
    public boolean updateUserById(User user) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_STATEMENT)) {
            LOGGER.debug("Updating user into DB");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setDate(2, Date.valueOf(user.getBirthday()));
            preparedStatement.setInt(3, user.getLoginId());
            preparedStatement.setString(4, user.getCity());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getDescription());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setInt(8, user.getId());
            result = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException e) {
            LOGGER.error("Error during updating user by Id {}", user, e);
        }
        return result;
    }

    /**
     * Delete user by ID.
     *
     * @param id of the user to be deleted.
     * @return {@code true} if object was deleted successfully.
     */
    @Override
    public boolean deleteUserById(Integer id) {
        boolean result = false;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_STATEMENT)) {
            LOGGER.debug("Deleting user from DB");
            preparedStatement.setInt(1, id);
            result = (preparedStatement.executeUpdate() == 1);
        } catch (SQLException e) {
            LOGGER.error("Error during deleting object by Id {}", id, e);
        }
        return result;
    }

    /**
     * Get all users from DB.
     *
     * @return collection of users from DB.
     */
    @Override
    public Collection<User> getAllUsers() {
        List<User> lstmb = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            LOGGER.debug("Getting all users from DB");
            while (resultSet.next()) {
                User user = new User();
                createUserObject(user, resultSet);
                lstmb.add(user);
            }
            return lstmb;
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getAllUsers method", e);
        }
        return new ArrayList<>();
    }

    /**
     * Verify if user with tha following email and password exists.
     * @param user to be verified.
     * @return {@code true} if user with tha following email and password exists
     */
    @Override
    public boolean verifyUser(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASSWORD_STATEMENT)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("Error during verifying user", e);
        }
        return false;
    }
}
