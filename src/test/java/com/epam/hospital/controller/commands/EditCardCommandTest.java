package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.data_access_layer.models.Category;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Role;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.converters.HospitalStaffConverter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditCardCommandTest {

    private Command editCardCommand;

    @Mock
    private HospitalCardFacade hospitalCardFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private HospitalCardDto hospitalCardDto;

    private HospitalStaff hospitalStaff;

    private Role nurseRole;

    private Role doctorRole;

    @BeforeEach
    void init() {
        editCardCommand = new EditCardCommand(hospitalCardFacade);
        hospitalCardDto = new HospitalCardDto();
        hospitalCardDto.setId(1);
        hospitalStaff = new HospitalStaff();
        hospitalStaff.setCategory(new Category(1L, "Oncologist"));
        nurseRole = new Role(1, "NURSE");
        doctorRole = new Role(2, "DOCTOR");
    }

    @Test
    void executeGetForNurseSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("id")).thenReturn("1");

        hospitalStaff.setRole(nurseRole);
        hospitalCardDto.setDoctor(HospitalStaffConverter.convertHospitalStaffToDto(hospitalStaff));
        when(hospitalCardFacade.getHospitalCardById(1L)).thenReturn(hospitalCardDto);
        String returnValue = editCardCommand.execute(request, response);
        verify(hospitalCardFacade, times(1)).getHospitalCardById(1L);
        assertEquals(Path.EDIT_CARD_FOR_NURSE_PAGE, returnValue);
    }

    @Test
    void executeGetForDoctorSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("id")).thenReturn("1");

        hospitalStaff.setRole(doctorRole);
        hospitalCardDto.setDoctor(HospitalStaffConverter.convertHospitalStaffToDto(hospitalStaff));
        when(hospitalCardFacade.getHospitalCardById(1L)).thenReturn(hospitalCardDto);
        String returnValue = editCardCommand.execute(request, response);
        verify(hospitalCardFacade, times(1)).getHospitalCardById(1L);
        assertEquals(Path.EDIT_CARD_FOR_DOCTOR_PAGE, returnValue);
    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("patientId")).thenReturn("1");
        when(request.getParameter("doctorId")).thenReturn("3");
        when(request.getParameter("procedures")).thenReturn("procedure");
        when(request.getParameter("medications")).thenReturn("medication");
        when(request.getParameter("operations")).thenReturn("operation");
        when(request.getParameter("diagnosis")).thenReturn("diagnosis");

        String returnValue = editCardCommand.execute(request, response);
        verify(hospitalCardFacade, times(1)).insertRecordToPatientProcedures(2L, "procedure");
        verify(hospitalCardFacade, times(1)).insertRecordToPatientMedications(2L, "medication");
        verify(hospitalCardFacade, times(1)).insertRecordToPatientOperations(2L, "operation");
        verify(hospitalCardFacade, times(1)).insertRecordToPatientDiagnosis(2L, "diagnosis");
        assertEquals(Path.REDIRECT, returnValue);
    }
}