package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Address;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public class UserDAOImp implements UserDAO{
    private AddressDAO addressDAO;
    private PassportDAO passportDAO;

    public UserDAOImp(AddressDAO addressDAO, PassportDAO passportDAO) {
        this.addressDAO = addressDAO;
        this.passportDAO = passportDAO;
    }

    public Optional<User> getByEmail(String email) {
        Connection connection = DbConnector.connectionDB();
        String sql = "SELECT * FROM \"user\" INNER JOIN passport ON \"user\".id = passport.user_id WHERE email=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String resultEmail = resultSet.getString(2);
                String password = resultSet.getString(3);
                Passport passport = new Passport(id, resultSet.getString(5));
                Set<Address> addresses = addressDAO.getAll(id);
                return Optional.of(new User(id, resultEmail, password, passport, addresses));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public int addUser(User user) throws SQLException {
        Connection connection = DbConnector.connectionDB();
        String sql = "INSERT INTO \"user\" (email, password) Values (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new SQLException("User creation error: failed to get ID.");
    }

    public int update(User user) {
        Connection connection = DbConnector.connectionDB();
        String sql = "UPDATE \"user\" SET email = ?, password = ? WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public int delete(String email) {
        Connection connection = DbConnector.connectionDB();
        String sql = "DELETE FROM \"user\" WHERE email = ?";
        int id = getByEmail(email).get().getId();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);
            passportDAO.delete(id);
            addressDAO.deleteAll(id);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        Connection connection = DbConnector.connectionDB();

        String sql = "SELECT * FROM \"user\" INNER JOIN passport ON \"user\".id = passport.user_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String resultEmail = resultSet.getString(2);
                String password = resultSet.getString(3);
                Passport passport = new Passport(id, resultSet.getString(5));
                Set<Address> addresses = addressDAO.getAll(id);
                User user = new User(id, resultEmail, password, passport, addresses);
                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }
}
