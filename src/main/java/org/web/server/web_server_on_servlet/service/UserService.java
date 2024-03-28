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
        this.userDb = new UserDb();
        this.addressDb = new AddressDb();
        this.passportDb = new PassportDb();
    }

    public String getUser(String email) {
        return new Gson().toJson(UserMapper.toDto(userDb.findByEmail(email).get()));
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

        if (userDb.findByEmail(userDTO.getEmail()).isEmpty()) {
            UserEntity userEntity = UserMapper.toEntity(userDTO);
            userDb.addUser(userEntity);
            userEntity = userDb.findByEmail(userEntity.getEmail()).get();

            PassportEntity passportEntity = PassportMapper.toEntity(userEntity.getId(), userDTO.getPassportDTO());
            passportDb.addPassport(passportEntity);

            for (AddressDTO addressDTO : userDTO.getAddresses()) {
                addressDb.add(AddressMapper.toEntity(userEntity.getId(), addressDTO));
            }
        }
    }

    public void deleteUser(String email) {
        if (!userDb.findByEmail(email).isEmpty()) {
            userDb.delete(email);
        }
    }
}
