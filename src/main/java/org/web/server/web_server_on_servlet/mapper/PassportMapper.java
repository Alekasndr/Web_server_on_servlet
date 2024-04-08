package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.entity.Passport;

public class PassportMapper {
    public static PassportDTO toDto(Passport entity) {
        return new PassportDTO(entity.getPassportNumber());
    }

    public static Passport toEntity(int userId, PassportDTO passportDTO) {
        return new Passport(userId, passportDTO.getPassportNumber());
    }
}
