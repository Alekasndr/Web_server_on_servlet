package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.PassportEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportDb {
    public static int addPassport(PassportEntity passportEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO passports (user_id, passportnumber) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, passportEntity.getUser_id());
            preparedStatement.setString(2, passportEntity.getPassportNumber());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static PassportEntity get(int id) throws SQLException {
        PassportEntity passportEntity = null;
        Connection connection = DbConnector.connectionDB();
        String sql = "SELECT * FROM passports WHERE user_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String passportNumber = resultSet.getString(2);
                passportEntity = new PassportEntity(user_id, passportNumber);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return passportEntity;
    }

    public static int update(PassportEntity passportEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "UPDATE passports SET passportnumber = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, passportEntity.getPassportNumber());
            preparedStatement.setInt(2, passportEntity.getUser_id());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int add(PassportEntity passportEntity) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO passports (user_id, passportnumber) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, passportEntity.getUser_id());
            preparedStatement.setString(2, passportEntity.getPassportNumber());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int delete(int user_id) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM passports WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }

    public static int clearTable() {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM passports";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return 0;
    }
}
