package org.web.server.web_server_on_servlet.controller.user_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AddUserServlet extends HttpServlet {
    UserService userService;

    public void init() {
        userService = new UserService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        userService.addUser(jsonData);

        PrintWriter out = response.getWriter();
        out.write("Added");
    }
}
