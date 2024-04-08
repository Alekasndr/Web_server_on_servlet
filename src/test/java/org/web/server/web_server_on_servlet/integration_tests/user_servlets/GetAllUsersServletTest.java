package org.web.server.web_server_on_servlet.integration_tests.user_servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.web.server.web_server_on_servlet.controller.user_servlets.GetAllUsersServlet;
import org.web.server.web_server_on_servlet.integration_tests.ServletTest;
import org.web.server.web_server_on_servlet.service.UserServiceImp;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetAllUsersServletTest extends ServletTest {
    @Test
    void getAllUsersServletTest() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        PrintWriter printWriter = Mockito.mock(PrintWriter.class);

        Gson gson = new Gson();
        String out = "";

        try {
            String jsonFilePath = "src/main/resources/test_jsons/integration_tests/get_all_users_servlet_test.json";
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
}
