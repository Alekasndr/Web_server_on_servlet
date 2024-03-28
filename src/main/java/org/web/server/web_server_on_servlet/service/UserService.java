package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.mapper.UserMapper;
import org.web.server.web_server_on_servlet.repository.AddressDb;
import org.web.server.web_server_on_servlet.repository.PassportDb;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.util.HashSet;
import java.util.Set;

public class UserService {
    public void addUser(String json) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        if (UserDb.findByEmail(userDTO.getEmail()).isEmpty()) {
            UserEntity userEntity = UserMapper.toEntity(userDTO);
            UserDb.addUser(userEntity);
            userEntity = UserDb.findByEmail(userEntity.getEmail()).get();

            PassportEntity passportEntity = PassportMapper.toEntity(userEntity.getId(), userDTO.getPassportDTO());
            PassportDb.addPassport(passportEntity);

            Set<AddressEntity> addressEntities = new HashSet<>();
            for (AddressDTO addressDTO : userDTO.getAddresses()) {
                addressEntities.add(AddressMapper.toEntity(userEntity.getId(), addressDTO));
            }
            AddressDb.addAll(addressEntities);
        }
    }
}
