package org.web.server.web_server_on_servlet.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PassportEntity implements Serializable {
    private int user_id;
    private String passportNumber;
}
