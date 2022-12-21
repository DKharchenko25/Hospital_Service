package com.epam.hospital.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Welcome", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getSession().getAttribute("user").toString();
        if (user.contains("ADMIN")) {
            request.getRequestDispatcher("adminWelcomePage.jsp").forward(request, response);
        } else if (user.contains("DOCTOR") || user.contains("NURSE")) {
            request.getRequestDispatcher("staffWelcomePage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("patientWelcomePage.jsp").forward(request, response);
        }
    }
}
