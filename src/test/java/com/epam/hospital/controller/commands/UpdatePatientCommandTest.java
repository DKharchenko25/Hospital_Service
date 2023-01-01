package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePatientCommandTest {

    private Command updatePatient;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private PatientDto patientDto;

    @BeforeEach
    void init() {
        updatePatient = new UpdatePatientCommand(patientFacade);
        patientDto = new PatientDto();
        patientDto.setId(2);
        patientDto.setUsername("Username1");
        patientDto.setPassword("TestPassword@123");
        patientDto.setFirstName("John");
        patientDto.setLastName("Dow");
        patientDto.setBirthDate("1994-12-05");
        patientDto.setEmail("example@mail.com");
        patientDto.setPhoneNumber("+380965432312");
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("id")).thenReturn("2");
        when(patientFacade.getPatientById(2L)).thenReturn(patientDto);
        String returnValue = updatePatient.execute(request, response);
        verify(patientFacade, times(1)).getPatientById(2L);
        assertEquals(Path.UPDATE_PATIENT_PAGE, returnValue);
    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("newUsername")).thenReturn(patientDto.getUsername());
        when(request.getParameter("newPassword")).thenReturn(patientDto.getPassword());
        when(request.getParameter("newFirstName")).thenReturn(patientDto.getFirstName());
        when(request.getParameter("newLastName")).thenReturn(patientDto.getLastName());
        when(request.getParameter("newBirthDate")).thenReturn(patientDto.getBirthDate());
        when(request.getParameter("newEmail")).thenReturn(patientDto.getEmail());
        when(request.getParameter("newPhoneNumber")).thenReturn(patientDto.getPhoneNumber());
        when(request.getSession()).thenReturn(session);
        doNothing().when(session).removeAttribute("existingUsername");

        doNothing().when(patientFacade).updatePatientUsernameById(patientDto.getId(), patientDto.getUsername());
        doNothing().when(patientFacade).updatePatientPasswordById(patientDto.getId(), patientDto.getPassword());
        doNothing().when(patientFacade).updatePatientFirstNameById(patientDto.getId(), patientDto.getFirstName());
        doNothing().when(patientFacade).updatePatientLastNameById(patientDto.getId(), patientDto.getLastName());
        doNothing().when(patientFacade).updatePatientBirthDateById(patientDto.getId(), patientDto.getBirthDate());
        doNothing().when(patientFacade).updatePatientEmailById(patientDto.getId(), patientDto.getEmail());
        doNothing().when(patientFacade).updatePatientPhoneNumberById(patientDto.getId(), patientDto.getPhoneNumber());

        String returnValue = updatePatient.execute(request, response);
        verify(patientFacade, times(1)).updatePatientUsernameById(patientDto.getId(), patientDto.getUsername());
        verify(patientFacade, times(1)).updatePatientPasswordById(patientDto.getId(), patientDto.getPassword());
        verify(patientFacade, times(1)).updatePatientFirstNameById(patientDto.getId(), patientDto.getFirstName());
        verify(patientFacade, times(1)).updatePatientLastNameById(patientDto.getId(), patientDto.getLastName());
        verify(patientFacade, times(1)).updatePatientBirthDateById(patientDto.getId(), patientDto.getBirthDate());
        verify(patientFacade, times(1)).updatePatientEmailById(patientDto.getId(), patientDto.getEmail());
        verify(patientFacade, times(1)).updatePatientPhoneNumberById(patientDto.getId(), patientDto.getPhoneNumber());

        assertEquals(Path.REDIRECT, returnValue);
    }

    @Test
    void executePostWrongUsername() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("newUsername")).thenReturn(patientDto.getUsername());
        when(request.getParameter("newPassword")).thenReturn(patientDto.getPassword());
        when(request.getParameter("newFirstName")).thenReturn(patientDto.getFirstName());
        when(request.getParameter("newLastName")).thenReturn(patientDto.getLastName());
        when(request.getParameter("newBirthDate")).thenReturn(patientDto.getBirthDate());
        when(request.getParameter("newEmail")).thenReturn(patientDto.getEmail());
        when(request.getParameter("newPhoneNumber")).thenReturn(patientDto.getPhoneNumber());
        when(request.getSession()).thenReturn(session);
        try (MockedStatic<PatientInputValidator> validator = mockStatic(PatientInputValidator.class)) {
            validator.when(() -> PatientInputValidator.isUsernameExists(patientDto.getUsername())).thenReturn(true);
        }
        String returnValue = updatePatient.execute(request, response);
        assertEquals(Path.REDIRECT, returnValue);
    }
}