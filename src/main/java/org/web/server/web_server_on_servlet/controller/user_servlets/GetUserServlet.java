package org.web.server.web_server_on_servlet.controller.user_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.UserService;
import org.web.server.web_server_on_servlet.service.UserServiceImp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class GetUserServlet extends HttpServlet {
    UserService userService;

    public GetUserServlet() {
    }

    public GetUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        userService = new UserServiceImp();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        PrintWriter out = response.getWriter();
        String outString = userService.getUser(jsonData);
        out.write(outString);
    }
}