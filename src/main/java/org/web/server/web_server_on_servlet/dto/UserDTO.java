package org.web.server.web_server_on_servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDTO {

    private String email;

    private String password;

    private PassportDTO passportDTO;

    private Set<AddressDTO> addresses;

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
