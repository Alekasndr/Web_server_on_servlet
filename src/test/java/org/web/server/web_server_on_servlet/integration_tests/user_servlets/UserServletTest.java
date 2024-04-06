package org.web.server.web_server_on_servlet.integration_tests.user_servlets;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.web.server.web_server_on_servlet.controller.user_servlets.DeleteUserServlet;
import org.web.server.web_server_on_servlet.controller.user_servlets.GetAllUsersServlet;
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

    @BeforeEach
    public void setup() {
        try {
            embeddedDatabase = H2Connector.createInMemoryDatabase();
            connection = embeddedDatabase.getConnection();
            try (MockedStatic<DbConnector> dbConnectorMockedStatic = Mockito.mockStatic(DbConnector.class)) {
                dbConnectorMockedStatic.when(DbConnector::connectionDB).thenReturn(connection);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }

    /*
    @Test
    public void shouldSaveAndFindUser() throws SQLException {
        try (MockedStatic<DbConnector> dbConnectorMockedStatic = Mockito.mockStatic(DbConnector.class)) {
            dbConnectorMockedStatic.when(DbConnector::connectionDB).thenReturn(connection);
        }
        userDAOImp.getByEmail("First");
    }
     */
    @Test
    public void getUserServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        String in = "{\n" +
                "    \"email\": \"Second\"\n" +
                "}";

        String out = "{\"email\":\"Second\",\"password\":\"2222\",\"passportDTO\":{\"passportNumber\":\"4321\"},\"addresses\":[{\"address\":\"address4\"},{\"address\":\"address6\"},{\"address\":\"address5\"}]}";

        ServletInputStream servletInputStream = new DelegatingServletInputStream(new ByteArrayInputStream(in.getBytes()));

        when(request.getInputStream())
                .thenReturn(servletInputStream);
        when(response.getWriter())
                .thenReturn(printWriter);

        GetUserServlet getUserServlet = new GetUserServlet(new UserServiceImp());
        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }

    @Test
    public void getAllUsersServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        String out = "[{\"email\":\"Second\",\"password\":\"2222\",\"passportDTO\":{\"passportNumber\":\"4321\"},\"addresses\":[{\"address\":\"address4\"},{\"address\":\"address6\"},{\"address\":\"address5\"}]},{\"email\":\"Third\",\"password\":\"3333\",\"passportDTO\":{\"passportNumber\":\"4567\"},\"addresses\":[{\"address\":\"address8\"},{\"address\":\"address7\"},{\"address\":\"address9\"}]}]";

        when(response.getWriter())
                .thenReturn(printWriter);

        GetAllUsersServlet getUserServlet = new GetAllUsersServlet(new UserServiceImp());
        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }

    @Test
    public void deleteUserServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        UserService userService = new UserServiceImp();
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(userService);

        String in = "{\n" +
                "    \"email\": \"First\"\n" +
                "}";

        ServletInputStream servletInputStream = new DelegatingServletInputStream(new ByteArrayInputStream(in.getBytes()));

        when(request.getInputStream())
                .thenReturn(servletInputStream);
        when(response.getWriter())
                .thenReturn(printWriter);

        deleteUserServlet.doDelete(request, response);

        GetAllUsersServlet getUserServlet = new GetAllUsersServlet(userService);
        String out = "[{\"email\":\"Second\",\"password\":\"2222\",\"passportDTO\":{\"passportNumber\":\"4321\"},\"addresses\":[{\"address\":\"address4\"},{\"address\":\"address6\"},{\"address\":\"address5\"}]},{\"email\":\"Third\",\"password\":\"3333\",\"passportDTO\":{\"passportNumber\":\"4567\"},\"addresses\":[{\"address\":\"address8\"},{\"address\":\"address7\"},{\"address\":\"address9\"}]}]";

        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }
}