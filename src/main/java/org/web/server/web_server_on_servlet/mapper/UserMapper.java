package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserDTO toDto(UserEntity entity) {
        Set<AddressDTO> addressDTOS = new HashSet<>();
        for (AddressEntity addressDTO : entity.getAddresses()) {
            addressDTOS.add(AddressMapper.toDto(addressDTO));
        }
        return new UserDTO(entity.getEmail(), entity.getPassword(), PassportMapper.toDto(entity.getPassportEntity()), addressDTOS);
    }

    public static UserEntity toEntity(UserDTO entity) {
        return new UserEntity(entity.getEmail(), entity.getPassword());
    }
}
