package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.DeleteDTO;
import org.web.server.web_server_on_servlet.dto.UserAddressDTO;
import org.web.server.web_server_on_servlet.entity.User;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.dao.AddressDAO;
import org.web.server.web_server_on_servlet.dao.PassportDAO;
import org.web.server.web_server_on_servlet.dao.UserDAO;

import java.util.Optional;

public class AddressService {
    private UserDAO userDAO;
    private AddressDAO addressDAO;

    public AddressService() {
        this.addressDAO = new AddressDAO();
        this.userDAO = new UserDAO(addressDAO, new PassportDAO());
    }

    public void addressService(String json) {
        Gson gson = new Gson();
        UserAddressDTO userAddressDTO = gson.fromJson(json, UserAddressDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(userAddressDTO.getEmail());
        if (optionalUser.isPresent()) {
            for (AddressDTO addressDTO : userAddressDTO.getAddresses()) {
                addressDAO.add(AddressMapper.toEntity(optionalUser.get().getId(), addressDTO));
            }
        }
    }

    public void deleteAddress(String json) {
        Gson gson = new Gson();
        DeleteDTO deleteDTO = gson.fromJson(json, DeleteDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(deleteDTO.getEmail());
        if (optionalUser.isPresent()) {
            addressDAO.delete(AddressMapper.toEntity(optionalUser.get().getId(), new AddressDTO(deleteDTO.getDeleteName())));
        }
    }
}
