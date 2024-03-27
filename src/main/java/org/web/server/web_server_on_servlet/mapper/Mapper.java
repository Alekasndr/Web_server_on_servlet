package org.web.server.web_server_on_servlet.mapper;

public interface Mapper<E, D> {
    E toEntity(D dto);

    D toDto(E entity);
}
