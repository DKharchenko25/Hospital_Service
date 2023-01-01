package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DischargeCommandTest {

    private Command dischargeCommand;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HospitalCardFacade hospitalCardFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void init() {
        dischargeCommand = new DischargeCommand(patientFacade, hospitalCardFacade);
    }

    @Test
    void executeSuccess() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        long id = Long.parseLong(request.getParameter("id"));
        doNothing().when(patientFacade).dischargePatientById(1L);
        doNothing().when(hospitalCardFacade).writeAndSendCardToPatient(id);
        String returnValue = dischargeCommand.execute(request, response);
        verify(patientFacade, times(1)).dischargePatientById(1L);
        verify(hospitalCardFacade, times(1)).writeAndSendCardToPatient(1L);
        assertEquals(Path.REDIRECT, returnValue);
    }
}