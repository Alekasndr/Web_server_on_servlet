package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AddressDb {
    public int add(AddressEntity addressEntity) {
        Connection connection = DbConnector.connectionDB();
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

    public static int addAll(Set<AddressEntity> addressEntities) {
        Connection connection = DbConnector.connectionDB();
        for (AddressEntity addressEntity : addressEntities) {
            String sql = "INSERT INTO addresses (user_id, address) Values (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, addressEntity.getUser_id());
                preparedStatement.setString(2, addressEntity.getAddress());
                preparedStatement.executeUpdate();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        return 1;
    }

    public static Set<AddressEntity> getAll(int id) throws SQLException {
        Set<AddressEntity> addresses = new HashSet<>();
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
                    AddressEntity addressEntity = new AddressEntity(address_id, user_id, passportNumber);
                    addresses.add(addressEntity);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return addresses;
    }

    public static int update(AddressEntity addressEntity) {
        Connection connection = DbConnector.connectionDB();
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

    public static int deleteAll(int user_id) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM addresses WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteOne(AddressEntity addressEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM addresses WHERE address = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, addressEntity.getAddress());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int clearTable() {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM addresses";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
