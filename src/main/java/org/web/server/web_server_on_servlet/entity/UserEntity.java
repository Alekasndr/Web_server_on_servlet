package org.web.server.web_server_on_servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserEntity implements Serializable {
    private int id;
    private String email;
    private String password;

    private PassportEntity passportEntity;
    private Set<AddressEntity> addresses;
}
