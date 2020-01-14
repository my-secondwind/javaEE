package com.inno.dao;

import com.inno.ConnectionManager.ConnectionManager;
import com.inno.pojo.Mobile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EJB
public class MobileDaoJdbcImpl implements MobileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(MobileDaoJdbcImpl.class);
    private static final String INSERT_INTO_MOBILE = "INSERT INTO mobile values (DEFAULT, ?, ?, ?)";
    private static final String SELECT_FROM_MOBILE = "SELECT * FROM mobile WHERE id = ?";
    private static final String SELECT_ALL_FROM_MOBILE = "SELECT * FROM mobile";
    private static final String UPDATE_MOBILE = "UPDATE mobile SET model=?, price=?, manufacturer=? WHERE id=?";
    private static final String DELETE_FROM_MOBILE = "DELETE FROM mobile WHERE id=?";

    private ConnectionManager connectionManager;

    @Inject
    public MobileDaoJdbcImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean addMobile(Mobile mobile) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_MOBILE)) {
            LOGGER.debug("Adding new mobile to DB");
            preparedStatement.setString(1, mobile.getModel());
            preparedStatement.setInt(2, mobile.getPrice());
            preparedStatement.setString(3, mobile.getManufacturer());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in addMobile method", e);
            return false;
        }
        return true;
    }

    @Override
    public Mobile getMobileById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_MOBILE)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                LOGGER.debug("Getting mobile by ID from DB");
                if (resultSet.next()) {
                    return new Mobile.MobileBuilder(resultSet.getInt(1)).
                            withModel(resultSet.getString(2)).
                            withPrice(resultSet.getInt(3)).
                            withManufacturer(resultSet.getString(4)).
                            build();
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return null;
    }

    @Override
    public boolean updateMobileById(Mobile mobile) {

        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_MOBILE)) {
            LOGGER.debug("Updating mobile into DB");
            preparedStatement.setString(1, mobile.getModel());
            preparedStatement.setInt(2, mobile.getPrice());
            preparedStatement.setString(3, mobile.getManufacturer());
            preparedStatement.setInt(4, mobile.getId());
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in updateMobileById method", e);
        }
        return false;
    }

    @Override
    public boolean deleteMobileById(Integer id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FROM_MOBILE)) {
            LOGGER.debug("Deleting mobile from DB");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in deleteMobileById method", e);
            return false;
        }
        return true;
    }

    @Override
    public Collection<Mobile> getAllMobile() {
        List<Mobile> lstmb = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FROM_MOBILE);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            LOGGER.debug("Getting all mobiles from DB");
            while (resultSet.next()) {
                lstmb.add(new Mobile.MobileBuilder(resultSet.getInt(1)).
                        withModel(resultSet.getString(2)).
                        withPrice(resultSet.getInt(3)).
                        withManufacturer(resultSet.getString(4)).
                        build());
            }
            return lstmb;
        } catch (SQLException e) {
            LOGGER.error("Some thing wrong in getMobileById method", e);
        }
        return new ArrayList<>();
    }
}
