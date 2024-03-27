package org.web.server.web_server_on_servlet.entity;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
public class AddressEntity implements Serializable {
    @NonNull
    private int id;
    private int user_id;
    private String address;

    public AddressEntity(int user_id, String address) {
        this.user_id = user_id;
        this.address = address;
    }
}
