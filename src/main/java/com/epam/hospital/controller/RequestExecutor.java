package com.epam.hospital.controller;

import com.epam.hospital.controller.commands.Command;
import com.epam.hospital.controller.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestExecutor {
    private static final String REDIRECT_VALUE = "redirect";

    public void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        String commandReturnValue = command.execute(request, response);
        if (!commandReturnValue.equals(REDIRECT_VALUE)) {
            request.getRequestDispatcher(commandReturnValue).forward(request, response);
        }
    }
}
