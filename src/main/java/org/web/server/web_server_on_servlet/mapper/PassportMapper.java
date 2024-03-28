package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.entity.PassportEntity;

public class PassportMapper {
    public static PassportDTO toDto(PassportEntity entity) {
        return new PassportDTO(entity.getPassportNumber());
    }

    public static PassportEntity toEntity(int user_id, PassportDTO entity) {
        return new PassportEntity(user_id, entity.getPassportNumber());
    }
}
