package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dao.*;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.DeleteDTO;
import org.web.server.web_server_on_servlet.dto.UserAddressDTO;
import org.web.server.web_server_on_servlet.entity.User;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;

import java.util.Optional;

public class AddressServiceImp implements AddressService {
    private UserDAO userDAO;
    private AddressDAO addressDAO;

    public AddressServiceImp() {
        this.addressDAO = new AddressDAOImp();
        this.userDAO = new UserDAOImp(addressDAO, new PassportDAOImp());
    }

    public void addressService(String userAddressData) {
        Gson gson = new Gson();
        UserAddressDTO userAddressDTO = gson.fromJson(userAddressData, UserAddressDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(userAddressDTO.getEmail());
        if (optionalUser.isPresent()) {
            for (AddressDTO addressDTO : userAddressDTO.getAddresses()) {
                addressDAO.add(AddressMapper.toEntity(optionalUser.get().getId(), addressDTO));
            }
        }
    }

    public void deleteAddress(String deleteAddressData) {
        Gson gson = new Gson();
        DeleteDTO deleteDTO = gson.fromJson(deleteAddressData, DeleteDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(deleteDTO.getEmail());
        if (optionalUser.isPresent()) {
            addressDAO.delete(AddressMapper.toEntity(optionalUser.get().getId(), new AddressDTO(deleteDTO.getDeleteName())));
        }
    }
}
