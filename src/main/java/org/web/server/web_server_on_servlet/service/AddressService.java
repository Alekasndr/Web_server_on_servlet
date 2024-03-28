package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.DeleteDTO;
import org.web.server.web_server_on_servlet.dto.UserAddressDTO;
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
        UserAddressDTO userAddressDTO = gson.fromJson(json, UserAddressDTO.class);

        UserEntity userEntity = userDb.getByEmail(userAddressDTO.getEmail());
        if (userEntity != null) {
            for (AddressDTO addressDTO : userAddressDTO.getAddresses()) {
                addressDb.add(AddressMapper.toEntity(userEntity.getId(), addressDTO));
            }
        }
    }

    public void deleteAddress(String json) {
        Gson gson = new Gson();
        DeleteDTO deleteDTO = gson.fromJson(json, DeleteDTO.class);

        UserEntity userEntity = userDb.getByEmail(deleteDTO.getEmail());
        if (userEntity != null) {
            addressDb.delete(AddressMapper.toEntity(userEntity.getId(), new AddressDTO(deleteDTO.getDeleteName())));
        }
    }
}
