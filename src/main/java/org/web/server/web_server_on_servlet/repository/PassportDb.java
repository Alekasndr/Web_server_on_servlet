package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.PassportEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassportDb {
    public static PassportEntity getPassportByUserId(int id) throws SQLException {
        PassportEntity passportEntity = null;
        Connection connection = DbConnector.connection;
        String sql = "SELECT * FROM passports WHERE user_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int user_id = resultSet.getInt(1);
                String passportNumber = resultSet.getString(2);
                passportEntity = new PassportEntity(user_id, passportNumber);
            }
        }
        return passportEntity;
    }
}
