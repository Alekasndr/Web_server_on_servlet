package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dao.*;
import org.web.server.web_server_on_servlet.dto.*;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.mapper.UserMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImp implements UserService {
    private UserDAO userDAO;
    private AddressDAO addressDAO;
    private PassportDAO passportDAO;

    public UserServiceImp() {
        this.addressDAO = new AddressDAOImp();
        this.passportDAO = new PassportDAOImp();
        this.userDAO = new UserDAOImp(addressDAO, passportDAO);
    }

    public String getUser(String emailData) {
        Gson gson = new Gson();
        EmailDTO emailDTO = gson.fromJson(emailData, EmailDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(emailDTO.getEmail());
        if (optionalUser.isPresent()) {
            UserDTO userDTO = UserMapper.toDto(optionalUser.get());
            return gson.toJson(userDTO);
        }
        throw new RuntimeException("User not found!");
    }

    public String getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : userDAO.getAll()) {
            userDTOs.add(UserMapper.toDto(user));
        }
        return new Gson().toJson(userDTOs);
    }

    public void addUser(String userData) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(userData, UserDTO.class);

        try {
            Optional<User> optionalUser = userDAO.getByEmail(userDTO.getEmail());
            if (optionalUser.isEmpty()) {
                User user = UserMapper.toEntity(userDTO);

                int createdUserId = userDAO.addUser(user);

                Passport passport = PassportMapper.toEntity(createdUserId, userDTO.getPassportDTO());
                passportDAO.addPassport(passport);

                for (AddressDTO addressDTO : userDTO.getAddresses()) {
                    addressDAO.add(AddressMapper.toEntity(createdUserId, addressDTO));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateUser(String userUpdateData) {
        Gson gson = new Gson();
        UserUpdateDTO userUpdateDTO = gson.fromJson(userUpdateData, UserUpdateDTO.class);

        userDAO.getByEmail(userUpdateDTO.getEmail())
                .ifPresent(user -> {
                    userDAO.update(UserMapper.toEntity(new UserDTO(userUpdateDTO.getEmail(), userUpdateDTO.getPassword())));
                });
    }

    public void deleteUser(String emailData) {
        Gson gson = new Gson();
        EmailDTO emailDTO = gson.fromJson(emailData, EmailDTO.class);

        userDAO.getByEmail(emailDTO.getEmail())
                .ifPresent(user -> {
                    userDAO.delete(user.getEmail());
                });
    }
}
