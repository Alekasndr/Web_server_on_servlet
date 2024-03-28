package org.web.server.web_server_on_servlet.controller.passport_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.PassportService;

import java.io.IOException;
import java.util.Scanner;

public class UpdatePassportServlet extends HttpServlet {
    PassportService passportService;

    public void init() {
        passportService = new PassportService();
    }

    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        passportService.updatePassport(jsonData);
    }
}