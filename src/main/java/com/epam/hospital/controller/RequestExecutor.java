package com.epam.hospital.controller;

import com.epam.hospital.controller.commands.Command;
import com.epam.hospital.controller.commands.CommandFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestExecutor {

    public void executeRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.getCommand(request);
        String commandReturnValue = command.execute(request, response);
        if (!commandReturnValue.equals(Path.REDIRECT)) {
            request.getRequestDispatcher(commandReturnValue).forward(request, response);
        }
    }
}
