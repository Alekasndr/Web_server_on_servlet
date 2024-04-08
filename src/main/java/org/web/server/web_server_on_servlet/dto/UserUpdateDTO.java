package org.web.server.web_server_on_servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class UserUpdateDTO {

    private String email;

    private String password;
}
