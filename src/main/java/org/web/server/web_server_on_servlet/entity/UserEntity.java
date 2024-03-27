package org.web.server.web_server_on_servlet.entity;

import lombok.Data;

import java.util.Set;

@Data
public class UserEntity {
    private int id;
    private String email;
    private String password;

    private PassportEntity passportEntity;
    private Set<AddressEntity> addresses;
}
