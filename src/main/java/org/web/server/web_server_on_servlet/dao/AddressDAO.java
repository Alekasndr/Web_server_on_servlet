package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Address;

import java.util.Set;

public interface AddressDAO {

    Set<Address> getAll(int id);

    int add(Address address);

    int delete(Address address);

    int deleteAll(int user_id);
}
