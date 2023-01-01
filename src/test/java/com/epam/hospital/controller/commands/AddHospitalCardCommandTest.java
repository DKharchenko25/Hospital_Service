package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Transient;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddHospitalCardCommandTest {

    private Command command;

    @Mock
    private HospitalCardFacade hospitalCardFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void init() {
        command = new AddHospitalCardCommand(hospitalCardFacade);
    }


    @Test
    void executeSuccess() throws ServletException, IOException {
        when(request.getParameter("patientId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("2");
        Long patientId = Long.parseLong(request.getParameter("patientId"));
        Long doctorId = Long.parseLong(request.getParameter("doctorId"));
        doNothing().when(hospitalCardFacade).addHospitalCard(patientId, doctorId);
        String returnValue = command.execute(request, response);
        verify(hospitalCardFacade, times(1)).addHospitalCard(patientId, doctorId);
        assertEquals(Path.REDIRECT, returnValue);
    }
}