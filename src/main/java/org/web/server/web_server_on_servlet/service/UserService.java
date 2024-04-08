package org.web.server.web_server_on_servlet.service;

public interface UserService {

    String getUser(String emailData);

    String getAllUsers();

    void addUser(String userData);

    void updateUser(String userUpdateData);

    void deleteUser(String emailData);
}
