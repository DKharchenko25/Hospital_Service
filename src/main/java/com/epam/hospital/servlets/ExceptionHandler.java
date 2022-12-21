package com.epam.hospital.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/exceptionHandler")
public class ExceptionHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String referer = request.getHeader("referer");
        request.setAttribute("referer", referer);

        if (statusCode == 500) {
            response.sendRedirect("serverError.jsp");
        } else if (statusCode == 404) {
            response.sendRedirect("notFoundError.jsp");
        }
    }
}
