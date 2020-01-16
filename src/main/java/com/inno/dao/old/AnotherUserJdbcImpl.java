package com.inno.dao.old;

import com.inno.ConnectionManager.ConnectionManager;
import com.inno.ConnectionManager.ConnectionManagerJdbcImpl;
import com.inno.dao.UserDaoJdbcImpl;
import com.inno.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AnotherUserJdbcImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoJdbcImpl.class);
    private static ConnectionManager connectionManager =
            ConnectionManagerJdbcImpl.getInstance();
    private static final String INSERT_USER_STATEMENT = "INSERT INTO users values (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_BY_ID_STATEMENT = "SELECT * FROM users WHERE id = ?";
    private static final String SELECT_USER_BY_EMAIL_PASSWORD_STATEMENT = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String UPDATE_USER_STATEMENT = "UPDATE users SET name = ?, birthday = ?, login_id = ?, " +
            "city = ?, email = ?, description = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_STATEMENT = "DELETE FROM users WHERE id = ?";
    private static final String SELECT_ALL_FROM_USERS = "SELECT * FROM users";

    /**
     * Add User into DB.
     *
     * @param user that is added to DB.
     * @return {@code true} if user was added successfully.
     */


    public boolean adding(User user) {
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

    public User getting(Integer id) {
        User user = new User.UserBuilder().build();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID_STATEMENT)) {
            LOGGER.debug("Getting user by Id from DB");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = createUserObject(new User.UserBuilder(), resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Error during getting object by Id {}", id, e);
        }
        return user;
    }

    /**
     * Set object's fields according to DB info.
     *
     * @param userBuilder object to be filled.
     * @param resultSet   with info about object.
     * @throws SQLException - if any SQL errors occurs.
     */
    private User createUserObject(User.UserBuilder userBuilder, ResultSet resultSet) throws SQLException {
        userBuilder.withId(resultSet.getInt(1));
        userBuilder.withName(resultSet.getString(2));
        userBuilder.withBirthday(resultSet.getString(3));
        userBuilder.withLoginId(resultSet.getInt(4));
        userBuilder.withCity(resultSet.getString(5));
        userBuilder.withEmail(resultSet.getString(6));
        userBuilder.withDescription(resultSet.getString(7));
        userBuilder.withPassword(resultSet.getString(8));
        return userBuilder.build();
    }

    /**
     * Update DB row for the following user.
     *
     * @param user updated user.
     * @return {@code true} if user was updated successfully.
     */

    public boolean updating(User user) {
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

    public boolean deleting(Integer id) {
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

    public Collection<User> gettingAll() {
        List<User> lstmb = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_USERS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            LOGGER.debug("Getting all users from DB");
            while (resultSet.next()) {
                lstmb.add(createUserObject(new User.UserBuilder(), resultSet));
            }
            return lstmb;
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getAllUsers method", e);
        }
        return new ArrayList<>();
    }

    /**
     * Verify if user with tha following email and password exists.
     *
     * @param user to be verified.
     * @return {@code true} if user with tha following email and password exists
     */

    public boolean verifying(User user) {
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
