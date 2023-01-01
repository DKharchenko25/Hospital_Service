package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePatientCommandTest {

    private Command deletePatientCommand;

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
        deletePatientCommand = new DeletePatientCommand(patientFacade, hospitalCardFacade);
    }

    @Test
    void executeSuccess() throws ServletException, IOException {
        List<HospitalCardDto> list = new ArrayList<>();

        when(request.getParameter("id")).thenReturn("2");
        when(hospitalCardFacade.getAllHospitalCards()).thenReturn(list);
        lenient().doNothing().when(hospitalCardFacade).deleteHospitalCardById(anyLong());
        doNothing().when(patientFacade).deletePatientById(2L);
        String returnValue = deletePatientCommand.execute(request, response);
        verify(patientFacade, times(1)).deletePatientById(2L);
        verify(hospitalCardFacade, times(1)).getAllHospitalCards();
        verify(hospitalCardFacade, times(list.size())).deleteHospitalCardById(anyLong());
        assertEquals(Path.REDIRECT, returnValue);
    }
}