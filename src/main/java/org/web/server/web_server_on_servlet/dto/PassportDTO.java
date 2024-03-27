package org.web.server.web_server_on_servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class PassportDTO {
    private int user_id;
    private String passportNumber;
}
