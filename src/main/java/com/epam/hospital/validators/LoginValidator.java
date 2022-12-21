package com.epam.hospital.validators;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginValidator {

    public static void wrongPasswordAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isPasswordWrong", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public static void wrongUsernameAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isUsernameWrong", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public static void nullRoleAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isRoleNull", true);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
