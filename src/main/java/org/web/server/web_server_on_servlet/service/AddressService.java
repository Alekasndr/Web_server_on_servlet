package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.repository.AddressDb;
import org.web.server.web_server_on_servlet.repository.PassportDb;
import org.web.server.web_server_on_servlet.repository.UserDb;

public class AddressService {
    private UserDb userDb;
    private AddressDb addressDb;

    public AddressService() {
        this.addressDb = new AddressDb();
        this.userDb = new UserDb(addressDb, new PassportDb());
    }

    public void addressService(String json) {
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        UserEntity userEntity = userDb.getByEmail(userDTO.getEmail());
        if (userEntity != null) {
            for (AddressDTO addressDTO : userDTO.getAddresses()) {
                addressDb.add(AddressMapper.toEntity(userEntity.getId(), addressDTO));
            }
        }
    }

    public void deleteAddress(String json){

    }
}
