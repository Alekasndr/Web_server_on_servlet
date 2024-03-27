package org.web.server.web_server_on_servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AddressEntity implements Serializable {
    private int id;
    private int user_id;
    private String address;
}
