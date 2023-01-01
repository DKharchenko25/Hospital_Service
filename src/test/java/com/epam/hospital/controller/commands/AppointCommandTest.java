package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointCommandTest {

    private Command appointCommand;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void init() {
        appointCommand = new AppointCommand(patientFacade, hospitalStaffFacade);
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        List<HospitalStaffDto> list = new ArrayList<>();
        PatientDto patientDto = new PatientDto();
        patientDto.setId(2);

        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("patientId")).thenReturn("2");
        when(hospitalStaffFacade.getAllHospitalStaff()).thenReturn(list);
        when(patientFacade.getPatientById(2L)).thenReturn(patientDto);
        String returnValue = appointCommand.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaff();
        verify(patientFacade, times(1)).getPatientById(2L);
        assertEquals(Path.APPOINT_PAGE, returnValue);

    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("patientId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("2");
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        doNothing().when(patientFacade).appointPatientToDoctor(patientId, doctorId);
        String returnValue = appointCommand.execute(request, response);
        verify(patientFacade, times(1)).appointPatientToDoctor(patientId, doctorId);
        assertEquals(Path.REDIRECT, returnValue);
    }
}