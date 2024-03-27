package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.utils.DbConnector;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AddressDb {
    public static Set<AddressEntity> getAllAddressesById(int id) throws SQLException {
        Set<AddressEntity> addresses = new HashSet<>();
        Connection connection = DbConnector.connection;
        try {
            String sql = "SELECT * FROM addresses WHERE user_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int address_id = resultSet.getInt(1);
                    int user_id = resultSet.getInt(2);
                    String passportNumber = resultSet.getString(3);
                    AddressEntity addressEntity = new AddressEntity(address_id, user_id, passportNumber);
                    addresses.add(addressEntity);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return addresses;
    }

    public static int addAddressToUser(AddressEntity addressEntity) {
        Connection connection = DbConnector.connection;
        String sql = "INSERT INTO addresses (user_id, address) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, addressEntity.getUser_id());
            preparedStatement.setString(2, addressEntity.getAddress());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int updateAddressToUser(AddressEntity addressEntity) {
        Connection connection = DbConnector.connection;
        String sql = "UPDATE addresses SET user_id = ?, address = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, addressEntity.getUser_id());
            preparedStatement.setString(2, addressEntity.getAddress());
            preparedStatement.setInt(3, addressEntity.getUser_id());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteAllFromUser(int user_id) {
        Connection connection = DbConnector.connection;
        String sql = "DELETE FROM addresses WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteOneFromUser(AddressEntity addressEntity) {
        Connection connection = DbConnector.connection;
        String sql = "DELETE FROM addresses WHERE address = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, addressEntity.getAddress());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteAll() {
        Connection connection = DbConnector.connection;
        String sql = "DELETE FROM addresses";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
