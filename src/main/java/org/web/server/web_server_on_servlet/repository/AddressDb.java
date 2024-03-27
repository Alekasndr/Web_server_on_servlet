package org.web.server.web_server_on_servlet.repository;

import org.web.server.web_server_on_servlet.entity.AddressEntity;

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
}
