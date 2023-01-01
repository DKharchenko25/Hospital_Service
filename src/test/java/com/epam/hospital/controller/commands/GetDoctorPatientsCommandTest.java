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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetDoctorPatientsCommandTest {

    private Command getPatients;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private HospitalStaffDto hospitalStaffDto;

    private List<PatientDto> patientDtoList;

    @BeforeEach
    void init() {
        getPatients = new GetDoctorPatientsCommand(hospitalStaffFacade, patientFacade);
        hospitalStaffDto = new HospitalStaffDto();
        hospitalStaffDto.setId(1L);
        patientDtoList = new ArrayList<>();
    }

    @Test
    void executeSuccessForAdmin() throws ServletException, IOException {
        session.setAttribute("role", "ADMIN");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("ADMIN");

        when(hospitalStaffFacade.getHospitalStaffById(1L)).thenReturn(hospitalStaffDto);
        when(patientFacade.getAllPatientsByHospitalStaffId(1L)).thenReturn(patientDtoList);

        String returnValue = getPatients.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getHospitalStaffById(1L);
        verify(patientFacade, times(1)).getAllPatientsByHospitalStaffId(1L);
        assertEquals(Path.GET_DOCTOR_PATIENTS_PAGE, returnValue);
    }

    @Test
    void executeSuccessForStaff() throws ServletException, IOException {
        session.setAttribute("role", "DOCTOR");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("role")).thenReturn("DOCTOR");

        when(hospitalStaffFacade.getHospitalStaffById(1L)).thenReturn(hospitalStaffDto);
        when(patientFacade.getAllPatientsByHospitalStaffId(1L)).thenReturn(patientDtoList);

        String returnValue = getPatients.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getHospitalStaffById(1L);
        verify(patientFacade, times(1)).getAllPatientsByHospitalStaffId(1L);
        assertEquals(Path.GET_DOCTOR_PATIENTS_FOR_STAFF_PAGE, returnValue);
    }
}