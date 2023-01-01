package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.utils.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getSession().getAttribute("role").toString();
        if (user.equals(Roles.ADMIN.toString())) {
            return Path.ADMIN_WELCOME_PAGE;
        } else if (user.equals(Roles.DOCTOR.toString()) || user.equals(Roles.NURSE.toString())) {
            return Path.STAFF_WELCOME_PAGE;
        } else {
            return Path.PATIENT_WELCOME_PAGE;
        }
    }
}
