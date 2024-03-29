package org.web.server.web_server_on_servlet.service;

import com.google.gson.Gson;
import org.web.server.web_server_on_servlet.dto.PassportDTO;
import org.web.server.web_server_on_servlet.dto.UserPassportDTO;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;
import org.web.server.web_server_on_servlet.mapper.PassportMapper;
import org.web.server.web_server_on_servlet.dao.AddressDAO;
import org.web.server.web_server_on_servlet.dao.PassportDAO;
import org.web.server.web_server_on_servlet.dao.UserDAO;

import java.util.Optional;

public class PassportServiceImp implements PassportService{
    private UserDAO userDAO;
    private PassportDAO passportDAO;

    public PassportServiceImp() {
        this.passportDAO = new PassportDAO();
        this.userDAO = new UserDAO(new AddressDAO(), passportDAO);
    }

    public void updatePassport(String userPassportData) {
        Gson gson = new Gson();
        UserPassportDTO userPassportDTO = gson.fromJson(userPassportData, UserPassportDTO.class);

        Optional<User> optionalUser = userDAO.getByEmail(userPassportDTO.getEmail());
        if (optionalUser.isPresent()) {
            Passport passport = PassportMapper.toEntity(optionalUser.get().getId(), new PassportDTO(userPassportDTO.getPassportNumber()));
            passportDAO.update(passport);
        }
    }
}
