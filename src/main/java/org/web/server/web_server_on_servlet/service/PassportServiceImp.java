package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dao.*;
import org.web.server.web_server_on_servlet.dto.AddressDTO;
import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.dto.UserPassportDTO;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;
import org.web.server.web_server_on_servlet.mapper.AddressMapper;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;

import java.util.Optional;

public class PassportServiceImp implements PassportService{
    private UserDAO userDAO;
    private PassportDAO passportDAO;

    public PassportServiceImp() {
        this.passportDAO = new PassportDAOImp();
        this.userDAO = new UserDAOImp(new AddressDAOImp(), passportDAO);
    }

    public void updatePassport(String userPassportData) {
        Gson gson = new Gson();
        UserPassportDTO userPassportDTO = gson.fromJson(userPassportData, UserPassportDTO.class);

        userDAO.getByEmail(userPassportDTO.getEmail())
                .ifPresent(
                        user ->{
                                Passport passport = PassportMapper.toEntity(user.getId(), new PassportDTO(userPassportDTO.getPassportNumber()));
                                passportDAO.update(passport);
                        }
                );
    }
}
