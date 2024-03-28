package org.web.server.web_server_on_servlet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class DeleteDTO {
    private String email;
    private String deleteName;
}
