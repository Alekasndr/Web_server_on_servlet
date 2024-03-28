package org.web.server.web_server_on_servlet.controller.user_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

public class GetUserServlet extends HttpServlet {
    UserService userService;

    public void init() {
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        out.write(userService.getUser(email));
    }
}