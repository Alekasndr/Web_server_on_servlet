package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Passport;

public interface PassportDAO {

    public int addPassport(Passport passport);

    public Passport get(int id);

    public int update(Passport passport);

    public int add(Passport passport);

    public int delete(int user_id);
}
