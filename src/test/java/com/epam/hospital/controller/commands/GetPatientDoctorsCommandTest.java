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
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPatientDoctorsCommandTest {

    private Command getPatientDoctors;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private List<HospitalStaffDto> hospitalStaffDtoList;

    private PatientDto patientDto;

    @BeforeEach
    void init() {
        getPatientDoctors = new GetPatientDoctorsCommand(patientFacade, hospitalStaffFacade);
        patientDto = new PatientDto();
        hospitalStaffDtoList = new ArrayList<>();
    }

    @Test
    void executeSuccessForAdmin() throws ServletException, IOException {
        session.setAttribute("role", "ADMIN");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("ADMIN");

        when(patientFacade.getPatientById(1L)).thenReturn(patientDto);
        when(hospitalStaffFacade.getAllHospitalStaffByPatientId(1L)).thenReturn(hospitalStaffDtoList);
        String returnValue = getPatientDoctors.execute(request, response);
        verify(patientFacade, times(1)).getPatientById(1L);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffByPatientId(1L);
        assertEquals(Path.GET_PATIENT_DOCTORS_PAGE, returnValue);
    }

    @Test
    void executeSuccessForPatient() throws ServletException, IOException {
        session.setAttribute("role", "PATIENT");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("PATIENT");

        when(patientFacade.getPatientById(1L)).thenReturn(patientDto);
        when(hospitalStaffFacade.getAllHospitalStaffByPatientId(1L)).thenReturn(hospitalStaffDtoList);
        String returnValue = getPatientDoctors.execute(request, response);
        verify(patientFacade, times(1)).getPatientById(1L);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffByPatientId(1L);
        assertEquals(Path.GET_PATIENT_DOCTORS_FOR_PATIENT_PAGE, returnValue);
    }
}