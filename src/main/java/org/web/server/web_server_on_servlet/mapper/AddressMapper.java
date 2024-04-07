package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.entity.Address;

public class AddressMapper {
    public static AddressDTO toDto(Address entity) {
        return new AddressDTO(entity.getAddress());
    }

    public static Address toEntity(int userId, AddressDTO addressDTO) {
        return new Address(userId, addressDTO.getAddress());
    }
}
