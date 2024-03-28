package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.dto.UserPassportDTO;
import org.web.server.web_server_on_servlet.entity.PassportEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.repository.AddressDb;
import org.web.server.web_server_on_servlet.repository.PassportDb;
import org.web.server.web_server_on_servlet.repository.UserDb;

public class PassportService {
    private UserDb userDb;
    private PassportDb passportDb;

    public PassportService() {
        this.passportDb = new PassportDb();
        this.userDb = new UserDb(new AddressDb(), passportDb);
    }

    public void updatePassport(String json) {
        Gson gson = new Gson();
        UserPassportDTO userPassportDTO = gson.fromJson(json, UserPassportDTO.class);

        UserEntity userEntity = userDb.getByEmail(userPassportDTO.getEmail());
        if (userEntity != null) {
            PassportEntity passportEntity = PassportMapper.toEntity(userEntity.getId(), new PassportDTO(userPassportDTO.getPassportNumber()));
            passportDb.update(passportEntity);
        }
    }
}
