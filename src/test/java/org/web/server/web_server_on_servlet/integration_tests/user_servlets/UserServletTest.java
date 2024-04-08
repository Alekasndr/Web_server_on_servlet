package org.web.server.web_server_on_servlet.integration_tests.user_servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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

    private static Connection connection;

    @InjectMocks
    private UserDAOImp userDAOImp = new UserDAOImp(new AddressDAOImp(), new PassportDAOImp());

    @BeforeEach
    void setup() {
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
    void tearDown() {
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }

    @Test
    void getUserServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        String in = "{\n" +
                "    \"email\": \"Second\"\n" +
                "}";

        Gson gson = new Gson();
        String out = "";

        try {
            String jsonFilePath = "src/main/resources/test_jsons/integration_tests/get_user_servlet_test.json";
            File jsonFile = new File(jsonFilePath);

            JsonElement jsonTree = new JsonParser().parse(new FileReader(jsonFile));
            out = gson.toJson(jsonTree);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
    void getAllUsersServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        Gson gson = new Gson();
        String out = "";

        try {
            String jsonFilePath = "src/main/resources/test_jsons/integration_tests/get_all_and_delet_users_servlet_test.json";
            File jsonFile = new File(jsonFilePath);

            JsonElement jsonTree = new JsonParser().parse(new FileReader(jsonFile));
            out = gson.toJson(jsonTree);

        } catch (IOException e) {
            e.printStackTrace();
        }

        when(response.getWriter())
                .thenReturn(printWriter);

        GetAllUsersServlet getUserServlet = new GetAllUsersServlet(new UserServiceImp());
        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }

    @Test
    void deleteUserServletTest() throws IOException {
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

        Gson gson = new Gson();
        String out = "";

        try {
            String jsonFilePath = "src/main/resources/test_jsons/integration_tests/get_all_and_delet_users_servlet_test.json";
            File jsonFile = new File(jsonFilePath);

            JsonElement jsonTree = new JsonParser().parse(new FileReader(jsonFile));
            out = gson.toJson(jsonTree);

        } catch (IOException e) {
            e.printStackTrace();
        }

        getUserServlet.doGet(request, response);

        verify(printWriter).write(out);
    }
}