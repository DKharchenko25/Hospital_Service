package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutCommandTest {

    private Command logoutCommand;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void init() {
        logoutCommand = new LogoutCommand();
    }

    @Test
    void execute() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).invalidate();
        String returnValue = logoutCommand.execute(request, response);
        assertEquals(Path.REDIRECT, returnValue);
    }
}