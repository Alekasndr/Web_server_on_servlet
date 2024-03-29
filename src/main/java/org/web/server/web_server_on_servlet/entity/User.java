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
public class User implements Serializable {
    //uui id
    private int id;

    private String email;

    private String password;

    private Passport passport;

    private Set<Address> addresses;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
