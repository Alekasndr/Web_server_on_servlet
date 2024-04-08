package org.web.server.web_server_on_servlet.controller.user_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.UserService;
import org.web.server.web_server_on_servlet.service.UserServiceImp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class UpdateUserServlet extends HttpServlet {
    UserService userService;

    public UpdateUserServlet() {
    }

    public UpdateUserServlet(UserService userService) {
        this.userService = userService;
    }

    public void init() {
        userService = new UserServiceImp();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        userService.updateUser(jsonData);

        PrintWriter out = response.getWriter();
        out.write("Updated");
    }
}
