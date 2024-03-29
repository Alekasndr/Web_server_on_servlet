package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Passport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PassportDAO {
    public int addPassport(Passport passport) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO passports (user_id, passportnumber) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, passport.getUser_id());
            preparedStatement.setString(2, passport.getPassportNumber());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public Passport get(int id) {
        Passport passport = null;
        Connection connection = DbConnector.connectionDB();
        String sql = "SELECT * FROM passports WHERE user_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String passportNumber = resultSet.getString(2);
                passport = new Passport(user_id, passportNumber);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return passport;
    }

    public int update(Passport passport) {
        Connection connection = DbConnector.connectionDB();
        String sql = "UPDATE passports SET passportnumber = ? WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, passport.getPassportNumber());
            preparedStatement.setInt(2, passport.getUser_id());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int add(Passport passport) {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO passports (user_id, passportnumber) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, passport.getUser_id());
            preparedStatement.setString(2, passport.getPassportNumber());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(int user_id) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM passports WHERE user_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user_id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
