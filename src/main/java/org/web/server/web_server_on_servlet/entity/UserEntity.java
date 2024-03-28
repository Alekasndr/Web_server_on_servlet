package org.web.server.web_server_on_servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class UserEntity implements Serializable {
    private int id;
    private String email;
    private String password;

    private PassportEntity passportEntity;
    private Set<AddressEntity> addresses;

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
