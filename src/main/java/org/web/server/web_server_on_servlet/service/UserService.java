package org.web.server.web_server_on_servlet.service;

public interface UserService {

    public String getUser(String emailData);

    public String getAllUsers();

    public void addUser(String userData);

    public void updateUser(String userUpdateData);

    public void deleteUser(String emailData);
}
