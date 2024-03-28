package org.web.server.web_server_on_servlet.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.mapper.UserMapper;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.io.IOException;
import java.io.PrintWriter;

public class GetUserServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();
        String string = new Gson().toJson(UserMapper.toDto(UserDb.get(email)));
        out.write(string);
    }
}