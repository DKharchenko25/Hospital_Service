package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandlerCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = throwable.getMessage();
        String referer = request.getHeader("referer");
        request.setAttribute("statusCode", statusCode);
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("referer", referer);
        return Path.ERROR_PAGE;
    }
}
