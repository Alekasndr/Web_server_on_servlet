package org.web.server.web_server_on_servlet.controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.UserService;

import java.io.IOException;
import java.util.Scanner;

public class UserUpdateServlet extends HttpServlet {
    UserService userService;

    public void init() {
        userService = new UserService();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException{
        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        userService.updateUser(jsonData);
    }
}
