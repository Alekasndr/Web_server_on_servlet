package org.web.server.web_server_on_servlet.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.web.server.web_server_on_servlet.dto.UserDTO;
import org.web.server.web_server_on_servlet.entity.UserEntity;
import org.web.server.web_server_on_servlet.mapper.UserMapper;
import org.web.server.web_server_on_servlet.repository.UserDb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class GetAllUsersServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<UserDTO> userDTOs = new ArrayList<>();
        for (UserEntity userEntity : UserDb.getAll()) {
            userDTOs.add(UserMapper.toDto(userEntity));
        }

        String string = new Gson().toJson(userDTOs);
        out.write(string);
    }
}
