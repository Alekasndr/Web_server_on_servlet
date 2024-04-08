package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO {

    Optional<User> getByEmail(String email);

    int addUser(User user) throws SQLException;

    int update(User user);

    int delete(String email);

    ArrayList<User> getAll();
}
