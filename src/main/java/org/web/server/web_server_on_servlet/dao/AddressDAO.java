package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Address;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AddressDAO {
    public Set<Address> getAll(int id) {
        Set<Address> addresses = new HashSet<>();
        Connection connection = DbConnector.connectionDB();
        try {
            String sql = "SELECT * FROM addresses WHERE user_id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int address_id = resultSet.getInt(1);
                    int user_id = resultSet.getInt(2);
                    String passportNumber = resultSet.getString(3);
                    Address address = new Address(address_id, user_id, passportNumber);
                    addresses.add(address);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return addresses;
    }

    public int add(Address address) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO addresses (user_id, address) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, address.getUser_id());
            preparedStatement.setString(2, address.getAddress());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(Address address) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM addresses WHERE address = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, address.getAddress());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int deleteAll(int user_id) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM addresses WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
