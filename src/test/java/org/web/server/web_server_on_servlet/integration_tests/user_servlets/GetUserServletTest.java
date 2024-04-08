package org.web.server.web_server_on_servlet.integration_tests.user_servlets;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.web.server.web_server_on_servlet.controller.user_servlets.GetUserServlet;
import org.web.server.web_server_on_servlet.integration_tests.ServletTest;
import org.web.server.web_server_on_servlet.service.UserServiceImp;

import java.io.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetUserServletTest extends ServletTest {
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
}
