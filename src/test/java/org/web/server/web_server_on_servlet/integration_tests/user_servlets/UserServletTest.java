package org.web.server.web_server_on_servlet.integration_tests.user_servlets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.web.server.web_server_on_servlet.controller.user_servlets.GetUserServlet;
import org.web.server.web_server_on_servlet.dao.AddressDAOImp;
import org.web.server.web_server_on_servlet.dao.DbConnector;
import org.web.server.web_server_on_servlet.dao.PassportDAOImp;
import org.web.server.web_server_on_servlet.dao.UserDAOImp;
import org.web.server.web_server_on_servlet.integration_tests.H2Connector;
import org.web.server.web_server_on_servlet.service.UserService;
import org.web.server.web_server_on_servlet.service.UserServiceImp;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@DataJpaTest
public class UserServletTest {
    private static EmbeddedDatabase embeddedDatabase;

    @Autowired
    private static TestEntityManager entityManager;

    private static Connection connection;

    @InjectMocks
    private UserDAOImp userDAOImp = new UserDAOImp(new AddressDAOImp(), new PassportDAOImp());

    @BeforeAll
    public static void setup() {
        try {
            embeddedDatabase = H2Connector.createInMemoryDatabase();
            connection = embeddedDatabase.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }

    @Test
    public void shouldSaveAndFindUser() throws SQLException {
        try (MockedStatic<DbConnector> dbConnectorMockedStatic = Mockito.mockStatic(DbConnector.class)) {
            dbConnectorMockedStatic.when(DbConnector::connectionDB).thenReturn(connection);
        }
        userDAOImp.getByEmail("First");
    }

    @Test
    public void getUserServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        String in = "{\n" +
                "    \"email\": \"First\"\n" +
                "}";

        String out = "{\"email\":\"First\",\"password\":\"1111\",\"passportDTO\":{\"passportNumber\":\"1234\"},\"addresses\":[{\"address\":\"address3\"},{\"address\":\"address2\"},{\"address\":\"address1\"}]}";

        ServletInputStream servletInputStream = new DelegatingServletInputStream(new ByteArrayInputStream(in.getBytes()));

        when(request.getInputStream())
                .thenReturn(servletInputStream);
        when(response.getWriter())
                .thenReturn(printWriter);

        GetUserServlet getUserServlet = new GetUserServlet(new UserServiceImp());
        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }
}