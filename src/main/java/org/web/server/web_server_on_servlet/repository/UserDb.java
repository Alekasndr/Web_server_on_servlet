package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.utils.DbConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

public class UserDb {
    public static ArrayList<UserEntity> getAllUsers() {
        ArrayList<UserEntity> users = new ArrayList<>();
        Connection connection = DbConnector.connectionDB();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String password = resultSet.getString(3);
                PassportEntity passportEntity = PassportDb.getByUserId(id);
                Set<AddressEntity> addresses = AddressDb.getAllAddressesById(id);
                UserEntity user = new UserEntity(id, email, password, passportEntity, addresses);
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return users;
    }

    public static UserEntity getUserByEmail(String email) {
        UserEntity userEntity = null;
        Connection connection = DbConnector.connectionDB();
        String sql = "SELECT * FROM users WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String emailC = resultSet.getString(2);
                String password = resultSet.getString(3);
                PassportEntity passportEntity = PassportDb.getByUserId(id);
                Set<AddressEntity> addresses = AddressDb.getAllAddressesById(id);
                userEntity = new UserEntity(id, emailC, password, passportEntity, addresses);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userEntity;
    }

    public static int add(UserEntity userEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO users (email, password) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            PassportDb.insert(new PassportEntity(userEntity.getId(), userEntity.getPassportEntity().getPassportNumber()));
            for (AddressEntity addressEntity : userEntity.getAddresses()) {
                AddressDb.addAddressToUser(addressEntity);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int update(UserEntity userEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "UPDATE users SET email = ?, password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            preparedStatement.setString(3, userEntity.getEmail());
            PassportDb.update(new PassportEntity(userEntity.getId(), userEntity.getPassportEntity().getPassportNumber()));
            AddressDb.deleteAllFromUser(userEntity.getId());
            for (AddressEntity addressEntity : userEntity.getAddresses()) {
                AddressDb.addAddressToUser(addressEntity);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteUserByEmail(String email) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM users WHERE email = ?";
        int id = getUserByEmail(email).getId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            PassportDb.delete(id);
            AddressDb.deleteAllFromUser(id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int deleteAllUsers() {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            PassportDb.deleteAll();
            AddressDb.deleteAll();
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
