package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;

public class AddressMapper {
    public static AddressDTO toDto(AddressEntity entity) {
        return new AddressDTO(entity.getAddress());
    }

    public static AddressEntity toEntity(int user_id, AddressDTO entity) {
        return new AddressEntity(user_id, entity.getAddress());
    }
}
