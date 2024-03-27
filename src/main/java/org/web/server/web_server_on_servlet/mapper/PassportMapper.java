package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.entity.PassportEntity;

public class PassportMapper implements Mapper<PassportEntity, PassportDTO> {
    @Override
    public PassportEntity toEntity(PassportDTO dto) {
        return new PassportEntity(dto.getUser_id(), dto.getPassportNumber());
    }

    @Override
    public PassportDTO toDto(PassportEntity entity) {
        return new PassportDTO(entity.getUser_id(), entity.getPassportNumber());
    }
}
