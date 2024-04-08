package org.web.server.web_server_on_servlet.dao;

import org.web.server.web_server_on_servlet.entity.Passport;

public interface PassportDAO {

    int addPassport(Passport passport);

    Passport get(int id);

    int update(Passport passport);

    int add(Passport passport);

    int delete(int user_id);
}
