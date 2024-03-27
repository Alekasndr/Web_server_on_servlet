package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.utils.DbConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

public class UserDb {
    public static ArrayList<UserEntity> getAllUsers() throws SQLException {

        ArrayList<UserEntity> users = new ArrayList<UserEntity>();
        Connection connection = DbConnector.connection;
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
}
