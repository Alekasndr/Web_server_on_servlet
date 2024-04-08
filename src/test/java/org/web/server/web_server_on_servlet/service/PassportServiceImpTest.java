package org.web.server.web_server_on_servlet.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web.server.web_server_on_servlet.dao.PassportDAOImp;
import org.web.server.web_server_on_servlet.dao.UserDAOImp;
import org.web.server.web_server_on_servlet.entity.Address;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PassportServiceImpTest {
    @Mock
    private PassportDAOImp passportDAOImp = Mockito.mock(PassportDAOImp.class);

    @Mock
    private UserDAOImp userDAOImp = Mockito.mock(UserDAOImp.class);

    @InjectMocks
    private PassportServiceImp passportServiceImp = new PassportServiceImp();

    @Test
    void updatePassportTest() {
        String userPassportData = "{\n" +
                "        \"email\": \"qwert\",\n" +
                "        \"passportNumber\": \"1234\"\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(1, "qwer");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User(1, "email", "password", passport, addresses);
        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        when(passportDAOImp.update(any()))
                .thenReturn(1);

        passportServiceImp.updatePassport(userPassportData);

        verify(userDAOImp).getByEmail(any());
        verify(passportDAOImp).update(any());
    }
}
