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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WelcomeCommandTest {

    private Command welcomeCommand;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @BeforeEach
    void init() {
        welcomeCommand = new WelcomeCommand();
    }

    @Test
    void executeSuccessForAdmin() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("ADMIN");
        String returnValue = welcomeCommand.execute(request, response);
        assertEquals(Path.ADMIN_WELCOME_PAGE, returnValue);
    }

    @Test
    void executeSuccessForDoctorSuccess() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("DOCTOR");
        String returnValue = welcomeCommand.execute(request, response);
        assertEquals(Path.STAFF_WELCOME_PAGE, returnValue);
    }

    @Test
    void executeSuccessForNurseSuccess() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("NURSE");
        String returnValue = welcomeCommand.execute(request, response);
        assertEquals(Path.STAFF_WELCOME_PAGE, returnValue);
    }

    @Test
    void executeSuccessForPatientSuccess() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("PATIENT");
        String returnValue = welcomeCommand.execute(request, response);
        assertEquals(Path.PATIENT_WELCOME_PAGE, returnValue);
    }
}