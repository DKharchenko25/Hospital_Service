package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.data_access_layer.models.Category;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.data_access_layer.models.Role;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.converters.HospitalStaffConverter;
import com.epam.hospital.facades.converters.PatientConverter;
import com.epam.hospital.facades.dtos.HospitalCardDto;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveCardCommandTest {

    private Command removeCard;

    @Mock
    private HospitalCardFacade hospitalCardFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private HospitalCardDto hospitalCardDto;

    private HospitalStaff hospitalStaff;

    private Patient patient;

    @BeforeEach
    void init() {
        removeCard = new RemoveCardCommand(hospitalCardFacade);
        hospitalCardDto = new HospitalCardDto();
        hospitalStaff = new HospitalStaff();
        patient = new Patient();
        hospitalStaff.setId(3);
        hospitalStaff.setRole(new Role(1L, "DOCTOR"));
        hospitalStaff.setCategory(new Category(1L, "Oncologist"));
        patient.setId(2);
        hospitalCardDto.setDoctor(HospitalStaffConverter.convertHospitalStaffToDto(hospitalStaff));
        hospitalCardDto.setPatient(PatientConverter.convertPatientToDto(patient));
    }

    @Test
    void executeSuccess() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("2");
        when(hospitalCardFacade.getHospitalCardById(2L)).thenReturn(hospitalCardDto);
        doNothing().when(hospitalCardFacade).deleteHospitalCardById(2L);
        String returnValue = removeCard.execute(request, response);
        verify(hospitalCardFacade, times(1)).getHospitalCardById(2L);
        verify(hospitalCardFacade, times(1)).deleteHospitalCardById(2L);
        assertEquals(Path.REDIRECT, returnValue);
    }
}