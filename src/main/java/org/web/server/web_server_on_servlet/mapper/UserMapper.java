package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;
import org.web.server.web_server_on_servlet.entity.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMapper implements Mapper<UserEntity, UserDTO> {
    private PassportMapper passportMapper;
    private AddressMapper addressMapper;

    public UserMapper(PassportMapper passportMapper, AddressMapper addressMapper) {
        this.passportMapper = passportMapper;
        this.addressMapper = addressMapper;
    }

    @Override
    public UserEntity toEntity(UserDTO dto) {
        Set<AddressEntity> addressEntities = new HashSet<>();
        for (AddressDTO addressDTO : dto.getAddresses()) {
            addressEntities.add(addressMapper.toEntity(addressDTO));
        }
        return new UserEntity(dto.getEmail(), dto.getPassword(), passportMapper.toEntity(dto.getPassportDTO()), addressEntities);
    }

    @Override
    public UserDTO toDto(UserEntity entity) {
        Set<AddressDTO> addressDTOS = new HashSet<>();
        for (AddressEntity addressDTO : entity.getAddresses()) {
            addressDTOS.add(addressMapper.toDto(addressDTO));
        }
        return new UserDTO(entity.getEmail(), entity.getPassword(), passportMapper.toDto(entity.getPassportEntity()), addressDTOS);
    }
}
