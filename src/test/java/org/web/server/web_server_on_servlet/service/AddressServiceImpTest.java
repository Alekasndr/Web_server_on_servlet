package org.web.server.web_server_on_servlet.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web.server.web_server_on_servlet.dao.AddressDAOImp;
import org.web.server.web_server_on_servlet.dao.UserDAOImp;
import org.web.server.web_server_on_servlet.entity.Address;
import org.web.server.web_server_on_servlet.entity.Passport;
import org.web.server.web_server_on_servlet.entity.User;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImpTest {
    @Mock
    private AddressDAOImp addressDAOImp = Mockito.mock(AddressDAOImp.class);

    @Mock
    private UserDAOImp userDAOImp = Mockito.mock(UserDAOImp.class);

    @InjectMocks
    private AddressServiceImp addressServiceImp = new AddressServiceImp();

    @Test
    void addAddressTest() {
        String userAddressData = "{\n" + // можно в отдельный файл вынести
                "        \"email\": \"qwert\",\n" +
                "        \"addresses\": [\n" +
                "            {\n" +
                "                \"address\": \"12121\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"address\": \"ddsfsfd\"\n" +
                "            }\n" +
                "        ]\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(1, "qwer");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User(1, "email", "password", passport, addresses);
        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        when(addressDAOImp.add(any()))
                .thenReturn(1);

        addressServiceImp.addAddress(userAddressData);

        verify(userDAOImp).getByEmail(any());
        verify(addressDAOImp, times(2)).add(any());
    }

    @Test
    public void deleteAddressTest() {
        String deleteAddressData = "{\n" +
                "    \"email\": \"qwert\",\n" +
                "    \"deleteName\": \"ddsfsfd\"\n" +
                "}";

        Passport passport = new Passport(1, "1234");
        Address address = new Address(1, "qwer");
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);

        User user = new User(1, "email", "password", passport, addresses);
        when(userDAOImp.getByEmail(any()))
                .thenReturn(Optional.of(user));
        when(addressDAOImp.delete(any()))
                .thenReturn(1);

        addressServiceImp.deleteAddress(deleteAddressData);

        verify(userDAOImp).getByEmail(any());
        verify(addressDAOImp).delete(any());
    }
}
