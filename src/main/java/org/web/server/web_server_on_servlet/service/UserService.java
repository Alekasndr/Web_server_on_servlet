package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.mapper.UserMapper;
import org.web.server.web_server_on_servlet.repository.AddressDb;
import org.web.server.web_server_on_servlet.repository.PassportDb;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private UserDb userDb;
    private AddressDb addressDb;
    private PassportDb passportDb;

    public UserService() {
        this.addressDb = new AddressDb();
        this.passportDb = new PassportDb();
        this.userDb = new UserDb(addressDb, passportDb);
    }

    public String getUser(String email) {
        if (userDb.getByEmail(email) != null) {
            return new Gson().toJson(UserMapper.toDto(userDb.getByEmail(email)));
        }
        return "User doesnt exist!";
    }

    public String getAllUsers() {
        List<UserDTO> userDTOs = new ArrayList<>();
        for (UserEntity userEntity : userDb.getAll()) {
            userDTOs.add(UserMapper.toDto(userEntity));
        }
        return new Gson().toJson(userDTOs);
    }

    public void addUser(String json) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        if (userDb.getByEmail(userDTO.getEmail()) == null) {
            UserEntity userEntity = UserMapper.toEntity(userDTO);
            userDb.addUser(userEntity);
            userEntity = userDb.getByEmail(userEntity.getEmail());

            PassportEntity passportEntity = PassportMapper.toEntity(userEntity.getId(), userDTO.getPassportDTO());
            passportDb.addPassport(passportEntity);

            for (AddressDTO addressDTO : userDTO.getAddresses()) {
                addressDb.add(AddressMapper.toEntity(userEntity.getId(), addressDTO));
            }
        }
    }

    public void updateUser(String json) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        if (userDb.getByEmail(userDTO.getEmail()) != null) {
            UserEntity userEntity = UserMapper.toEntity(userDTO);
            userDb.update(userEntity);
        }
    }

    public void deleteUser(String email) {
        if (userDb.getByEmail(email) != null) {
            userDb.delete(email);
        }
    }
}
