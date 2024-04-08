package org.web.server.web_server_on_servlet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web.server.web_server_on_servlet.dao.AddressDAOImp;
import org.web.server.web_server_on_servlet.dao.PassportDAOImp;
import org.web.server.web_server_on_servlet.dao.UserDAOImp;
import org.web.server.web_server_on_servlet.entity.Address;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {
    @Mock
    private AddressDAOImp addressDAOImp = Mockito.mock(AddressDAOImp.class);

    @Mock
    private PassportDAOImp passportDAOImp = Mockito.mock(PassportDAOImp.class);

    @Mock
    private UserDAOImp userDAOImp = Mockito.mock(UserDAOImp.class);

    @InjectMocks
    private UserServiceImp userServiceImp = new UserServiceImp();

    @Test
    void getUserTest() {
        String emailData = "{\n" +
                "    \"email\": \"qwert\"\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(1, "qwer");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User(1, "email", "password", passport, addresses);
        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        UserServiceImp userService = userServiceImp;

        String gettedUser = "{\"email\":\"email\",\"password\":\"password\",\"passportDTO\":{\"passportNumber\":\"1234\"},\"addresses\":[{\"address\":\"qwer\"}]}";

        Assertions.assertEquals(gettedUser, userService.getUser(emailData));
    }

    @Test
    void getAllUsersTest() {
        Passport passport = new Passport(1, "1234");
        Address address = new Address(1, "qwer");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User(1, "email", "password", passport, addresses);
        User user1 = new User(2, "email1", "password1", passport, addresses);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user1);

        when(userDAOImp.getAll())
                .thenReturn(users);
        UserServiceImp userService = userServiceImp;

        String gettedUsers = "[{\"email\":\"email\",\"password\":\"password\",\"passportDTO\":{\"passportNumber\":\"1234\"},\"addresses\":[{\"address\":\"qwer\"}]},{\"email\":\"email1\",\"password\":\"password1\",\"passportDTO\":{\"passportNumber\":\"1234\"},\"addresses\":[{\"address\":\"qwer\"}]}]";

        Assertions.assertEquals(gettedUsers, userService.getAllUsers());
    }

    @Test
    void addUserTest() throws SQLException {
        String userData = "{\n" +
                "    \"email\": \"qwert\",\n" +
                "    \"password\": \"22222\",\n" +
                "    \"passportDTO\": {\n" +
                "        \"passportNumber\": \"1234\"\n" +
                "    },\n" +
                "    \"addresses\": [\n" +
                "        {\n" +
                "            \"address\": \"12121\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"address\": \"РµРЅsafsfР№С†\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        User user1 = new User(0, "qwert", "22222", null, null);

        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.empty());
        when(userDAOImp.addUser(user1))
                .thenReturn(1);
        when(passportDAOImp.addPassport(any()))
                .thenReturn(1);
        when(addressDAOImp.add(any()))
                .thenReturn(1);

        userServiceImp.addUser(userData);

        verify(userDAOImp).getByEmail(any());
        verify(userDAOImp).addUser(any());
        verify(passportDAOImp).addPassport(any());
        verify(addressDAOImp, times(2)).add(any());
    }

    @Test
    void updateUserTest() {
        String userUpdateData = "{\n" +
                "    \"email\": \"qwert\",\n" +
                "    \"password\": \"22222\"\n" +
                "\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(0, 1, "12121");
        Address address1 = new Address(0, 1, "РµРЅsafsfР№С†");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        addresses.add(address1);

        User user = new User(1, "qwert", "22222", passport, addresses);

        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        when(userDAOImp.update(any()))
                .thenReturn(1);

        userServiceImp.updateUser(userUpdateData);

        verify(userDAOImp).getByEmail(any());
        verify(userDAOImp).update(any());
    }

    @Test
    void deleteUserTest() {
        String emailData = "{\n" +
                "    \"email\": \"Forth\"\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(0, 1, "12121");
        Address address1 = new Address(0, 1, "РµРЅsafsfР№С†");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        addresses.add(address1);

        User user = new User(1, "qwert", "22222", passport, addresses);

        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        when(userDAOImp.delete(any()))
                .thenReturn(1);

        userServiceImp.deleteUser(emailData);

        verify(userDAOImp).getByEmail(any());
        verify(userDAOImp).delete(any());
    }
}
