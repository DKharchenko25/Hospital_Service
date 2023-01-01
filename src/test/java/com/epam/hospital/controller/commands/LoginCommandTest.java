package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PasswordEncoder;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginCommandTest {

    private Command loginCommand;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;


    @BeforeEach
    void init() {
        loginCommand = new LoginCommand(hospitalStaffFacade, patientFacade);
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        String returnValue = loginCommand.execute(request, response);
        assertEquals(Path.INDEX, returnValue);
    }

    @Test
    void executePostWithNullRole() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn("Username1");
        when(request.getParameter("password")).thenReturn("TestPassword@123");
        when(request.getParameter("role")).thenReturn(null);
        String returnValue = loginCommand.execute(request, response);
        assertEquals(Path.INDEX, returnValue);
    }
}