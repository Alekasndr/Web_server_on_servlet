package org.web.server.web_server_on_servlet.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Address implements Serializable {

    private int id;

    private int user_id;

    private String address;

    public Address(int user_id, String address) {
        this.user_id = user_id;
        this.address = address;
    }
}
