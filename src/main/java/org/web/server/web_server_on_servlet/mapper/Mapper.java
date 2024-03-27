package org.web.server.web_server_on_servlet.mapper;

import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.entity.AddressEntity;

public interface Mapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);
}
