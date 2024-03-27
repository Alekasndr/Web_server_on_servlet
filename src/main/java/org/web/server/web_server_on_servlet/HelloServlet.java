package org.web.server.web_server_on_servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.web.server.web_server_on_servlet.entity.PassportEntity;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        // Hello
        PrintWriter out = response.getWriter();

        PassportEntity passportEntity = new PassportEntity(1, "2134");
        PassportEntity passportEntity2 = new PassportEntity(2, "2134");
        PassportEntity passportEntity3 = new PassportEntity(2, "2134");

        List<PassportEntity> list = new ArrayList<>();
        list.add(passportEntity);
        list.add(passportEntity2);
        list.add(passportEntity3);


        String string = new Gson().toJson(list);
        out.write(string);
    }

    public void destroy() {
    }
}