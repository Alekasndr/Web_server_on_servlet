package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.Address;
import org.web.server.web_server_on_servlet.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserDTO toDto(User entity) {
        Set<AddressDTO> addressDTOS = new HashSet<>();
        for (Address addressDTO : entity.getAddresses()) {
            addressDTOS.add(AddressMapper.toDto(addressDTO));
        }
        return new UserDTO(entity.getEmail(), entity.getPassword(), PassportMapper.toDto(entity.getPassport()), addressDTOS);
    }

    public static User toEntity(UserDTO userDTO) {
        return new User(userDTO.getEmail(), userDTO.getPassword());
    }
}
