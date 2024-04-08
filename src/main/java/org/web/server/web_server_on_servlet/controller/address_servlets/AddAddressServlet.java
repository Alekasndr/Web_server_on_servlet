package org.web.server.web_server_on_servlet.controller.address_servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.service.AddressService;
import org.web.server.web_server_on_servlet.service.AddressServiceImp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class AddAddressServlet extends HttpServlet {
    AddressService addressService;

    public AddAddressServlet() {
    }

    public AddAddressServlet(AddressService addressService) {
        this.addressService = addressService;
    }

    public void init() {
        addressService = new AddressServiceImp();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Scanner scanner = new Scanner(request.getInputStream());
        String jsonData = scanner.useDelimiter("\\A").next();
        scanner.close();

        addressService.addAddress(jsonData);

        PrintWriter out = response.getWriter();
        out.write("Added");
    }
}
