package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dao.*;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.DeleteDTO;
import org.web.server.web_server_on_servlet.dto.UserAddressDTO;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;

public class AddressServiceImp implements AddressService {
    private UserDAO userDAO;
    private AddressDAO addressDAO;

    public AddressServiceImp() {
        this.addressDAO = new AddressDAOImp();
        this.userDAO = new UserDAOImp(addressDAO, new PassportDAOImp());
    }

    public void addAddress(String userAddressData) {
        Gson gson = new Gson();
        UserAddressDTO userAddressDTO = gson.fromJson(userAddressData, UserAddressDTO.class);

        userDAO.getByEmail(userAddressDTO.getEmail())
                .ifPresent(user -> {
                    for (AddressDTO addressDTO : userAddressDTO.getAddresses()) {
                        addressDAO.add(AddressMapper.toEntity(user.getId(), addressDTO));
                    }
                });
    }

    public void deleteAddress(String deleteAddressData) {
        Gson gson = new Gson();
        DeleteDTO deleteDTO = gson.fromJson(deleteAddressData, DeleteDTO.class);

        userDAO.getByEmail(deleteDTO.getEmail())
                .ifPresent(
                        user ->
                                addressDAO.delete(
                                        AddressMapper.toEntity(user.getId(), new AddressDTO(deleteDTO.getDeleteName()))
                                )
                );
    }
}
