package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.mapper.UserMapper;
import org.web.server.web_server_on_servlet.repository.AddressDb;
import org.web.server.web_server_on_servlet.repository.PassportDb;
import org.web.server.web_server_on_servlet.repository.UserDb;

public class PassportService {
    private UserDb userDb;
    private AddressDb addressDb;
    private PassportDb passportDb;

    public PassportService() {
        this.addressDb = new AddressDb();
        this.passportDb = new PassportDb();
        this.userDb = new UserDb(addressDb, passportDb);
    }

    public void updatePassport(String json) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        UserEntity userEntity = userDb.getByEmail(userDTO.getEmail());
        if (userEntity != null) {
            PassportEntity passportEntity = PassportMapper.toEntity(userEntity.getId(), userDTO.getPassportDTO());
            passportDb.update(passportEntity);
        }
    }
}
