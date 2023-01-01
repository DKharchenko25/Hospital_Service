package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
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
class GetPatientCommandTest {

    private Command getPatient;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private PatientDto patientDto;

    @BeforeEach
    void init() {
        getPatient = new GetPatientCommand(patientFacade);
        patientDto = new PatientDto();
    }

    @Test
    void executeSuccess() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(patientFacade.getPatientById(2L)).thenReturn(patientDto);
        String returnValue = getPatient.execute(request, response);
        verify(patientFacade, times(1)).getPatientById(2L);
        assertEquals(Path.GET_PATIENT_PAGE, returnValue);
    }
}