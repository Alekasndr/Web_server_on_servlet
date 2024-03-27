package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.UserEntity;

public class UserMapper implements Mapper<UserEntity, UserDTO> {
    @Override
    public UserEntity toEntity(UserDTO dto) {
        return new UserEntity(dto.getEmail(), dto.getPassword(), dto.getPassportEntity(), dto.getAddresses());
    }

    @Override
    public UserDTO toDto(UserEntity entity) {
        return new UserDTO(entity.getEmail(), entity.getPassword(), entity.getPassportEntity(), entity.getAddresses());
    }
}
