package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;

public abstract class AddressMapper implements Mapper<AddressEntity, AddressDTO> {

    @Override
    public AddressEntity toEntity(AddressDTO dto) {
        return new AddressEntity(dto.getUser_id(), dto.getAddress());
    }

    @Override
    public AddressDTO toDto(AddressEntity entity) {
        return new AddressDTO(entity.getUser_id(), entity.getAddress());
    }
}
