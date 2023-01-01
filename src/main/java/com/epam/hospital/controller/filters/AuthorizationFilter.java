package com.epam.hospital.controller.filters;

import com.epam.hospital.controller.Path;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

@WebFilter(filterName = "Authorization Filter", urlPatterns = "/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        String loginUri = request.getContextPath() + Path.LOGIN_COMMAND;
        String fullPath = request.getRequestURI() + "?" + request.getQueryString();
        boolean isLoggedIn = session != null && session.getAttribute("role") != null;
        boolean loginRequest = (fullPath).equals(loginUri);

        if (loginRequest) {
            chain.doFilter(req, resp);
            return;
        }

        if (isLoggedIn) {
            if (UserAccessContainer.checkAccess(request)) {
                chain.doFilter(req, resp);
            } else {
                throw new IllegalArgumentException(getErrorMessage(session));
            }
        } else {
            request.getRequestDispatcher(Path.INDEX).forward(request, response);
        }

    }

    private String getErrorMessage(HttpSession session) {
        Locale locale = new Locale(session.getAttribute("lang").toString());
        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle.getString("access.denied");
    }
}
