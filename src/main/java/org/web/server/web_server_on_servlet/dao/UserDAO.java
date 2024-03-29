package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface UserDAO {

    public Optional<User> getByEmail(String email);

    public int addUser(User user) throws SQLException;

    public int update(User user);

    public int delete(String email);

    public ArrayList<User> getAll();
}
