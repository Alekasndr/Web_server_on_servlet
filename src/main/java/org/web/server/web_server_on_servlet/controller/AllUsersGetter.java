package org.web.server.web_server_on_servlet.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.io.IOException;
import java.io.PrintWriter;

public class AllUsersGetter extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String string = new Gson().toJson(UserDb.getAllUsers());
        out.write(string);
    }
}
