package org.web.server.web_server_on_servlet.controller.address_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.AddressService;

import java.io.IOException;
import java.io.PrintWriter;

public class DeleteAddressServlet extends HttpServlet {
    AddressService addressService;

    public void init() {
        addressService = new AddressService();
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");

        addressService.deleteAddress(email);

        PrintWriter out = response.getWriter();
        out.write("Deleted");
    }
}
