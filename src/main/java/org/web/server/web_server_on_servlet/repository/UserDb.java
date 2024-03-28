package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class UserDb {
    public Optional<UserEntity> getByEmail(String email) {
        UserEntity userEntity = null;
        Connection connection = DbConnector.connectionDB();
        String sql = "SELECT * FROM users WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String email1 = resultSet.getString(2);
                String password = resultSet.getString(3);
                PassportEntity passportEntity = PassportDb.get(id);
                Set<AddressEntity> addresses = AddressDb.getAll(id);
                userEntity = new UserEntity(id, email1, password, passportEntity, addresses);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        if (userEntity == null) {
            return Optional.empty();
        } else {
            return Optional.of(userEntity);
        }
    }

    public int addUser(UserEntity userEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO users (email, password) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public int update(UserEntity userEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "UPDATE users SET email = ?, password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            preparedStatement.setString(3, userEntity.getEmail());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public int delete(String email) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM users WHERE email = ?";
        int id = getByEmail(email).get().getId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            PassportDb.delete(id);
            AddressDb.deleteAll(id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public ArrayList<UserEntity> getAll() {
        ArrayList<UserEntity> users = new ArrayList<>();
        Connection connection = DbConnector.connectionDB();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String email = resultSet.getString(2);
                String password = resultSet.getString(3);
                PassportEntity passportEntity = PassportDb.get(id);
                Set<AddressEntity> addresses = AddressDb.getAll(id);
                UserEntity user = new UserEntity(id, email, password, passportEntity, addresses);
                users.add(user);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return users;
    }
}
