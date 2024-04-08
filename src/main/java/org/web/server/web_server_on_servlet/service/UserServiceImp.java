package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dao.*;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.EmailDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.dto.UserUpdateDTO;
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
        return "User doesnt exist!"; // лучше не строку возвращать, а прокидывать исключение
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

        if (userDAO.getByEmail(userUpdateDTO.getEmail()).isPresent()) {
            User user = UserMapper.toEntity(new UserDTO(userUpdateDTO.getEmail(), userUpdateDTO.getPassword()));
            userDAO.update(user);
        }
    }

    public void deleteUser(String emailData) {
        Gson gson = new Gson();
        EmailDTO emailDTO = gson.fromJson(emailData, EmailDTO.class);

        if (userDAO.getByEmail(emailDTO.getEmail()).isPresent()) {
            userDAO.delete(emailDTO.getEmail());
        }
    }
}
