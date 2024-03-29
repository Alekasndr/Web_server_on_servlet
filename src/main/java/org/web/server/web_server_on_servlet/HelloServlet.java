package org.web.server.web_server_on_servlet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.web.server.web_server_on_servlet.entity.Passport;

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

        Passport passport = new Passport(1, "2134");
        Passport passport2 = new Passport(2, "2134");
        Passport passport3 = new Passport(2, "2134");

        List<Passport> list = new ArrayList<>();
        list.add(passport);
        list.add(passport2);
        list.add(passport3);


        String string = new Gson().toJson(list);
        out.write(string);
    }

    public void destroy() {
    }
}