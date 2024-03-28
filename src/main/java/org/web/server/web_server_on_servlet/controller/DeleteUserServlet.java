package org.web.server.web_server_on_servlet.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/delete")
public class DeleteUserServlet extends HttpServlet {
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        UserDb.delete(email);
        PrintWriter out = response.getWriter();
        out.write("Deleted");
    }
}
