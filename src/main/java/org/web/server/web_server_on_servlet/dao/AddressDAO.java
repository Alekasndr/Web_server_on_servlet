package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Address;

import java.util.Set;

public interface AddressDAO {

    public Set<Address> getAll(int id);

    public int add(Address address);

    public int delete(Address address);

    public int deleteAll(int user_id);
}
