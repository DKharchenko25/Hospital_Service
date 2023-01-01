package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LocaleHandlerCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = request.getParameter("lang");
        request.getSession().setAttribute("lang", lang);
        String referer = request.getHeader("referer");
        response.sendRedirect(referer);
        return Path.REDIRECT;
    }
}
