package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.utils.VerifyRecaptcha;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRegistrationCommandTest {

    private Command patientRegistration;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private PatientDto patientDto;

    private String confirmPassword;

    @BeforeEach
    void init() {
        patientRegistration = new PatientRegistrationCommand(patientFacade);
        patientDto = new PatientDto();
        patientDto.setUsername("Username1");
        patientDto.setPassword("TestPassword@123");
        patientDto.setFirstName("John");
        patientDto.setLastName("Dow");
        patientDto.setBirthDate("1995-03-27");
        patientDto.setEmail("example@mail.com");
        patientDto.setPhoneNumber("+380974567643");
        confirmPassword = patientDto.getPassword();
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        String returnValue = patientRegistration.execute(request, response);
        assertEquals(Path.REGISTER_PATIENT_PAGE, returnValue);
    }

    @Test
    void executePostWrongPassword() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(patientDto.getUsername());
        when(request.getParameter("password")).thenReturn(patientDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn("WrongPassword@123");
        when(request.getParameter("firstName")).thenReturn(patientDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(patientDto.getLastName());
        when(request.getParameter("birthDate")).thenReturn(patientDto.getBirthDate());
        when(request.getParameter("email")).thenReturn(patientDto.getEmail());
        when(request.getParameter("phoneNumber")).thenReturn(patientDto.getPhoneNumber());
        String returnValue = patientRegistration.execute(request, response);
        assertEquals(Path.REGISTER_PATIENT_PAGE, returnValue);
    }

    @Test
    void executePostWrongUsername() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(patientDto.getUsername());
        when(request.getParameter("password")).thenReturn(patientDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn(patientDto.getPassword());
        when(request.getParameter("firstName")).thenReturn(patientDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(patientDto.getLastName());
        when(request.getParameter("birthDate")).thenReturn(patientDto.getBirthDate());
        when(request.getParameter("email")).thenReturn(patientDto.getEmail());
        when(request.getParameter("phoneNumber")).thenReturn(patientDto.getPhoneNumber());
        try (MockedStatic<PatientInputValidator> validator = Mockito.mockStatic(PatientInputValidator.class)) {
            validator.when(() -> PatientInputValidator.isUsernameExists(patientDto.getUsername())).thenReturn(true);
            String returnValue = patientRegistration.execute(request, response);
            assertEquals(Path.REGISTER_PATIENT_PAGE, returnValue);
        }
    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(patientDto.getUsername());
        when(request.getParameter("password")).thenReturn(patientDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn(confirmPassword);
        when(request.getParameter("firstName")).thenReturn(patientDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(patientDto.getLastName());
        when(request.getParameter("birthDate")).thenReturn(patientDto.getBirthDate());
        when(request.getParameter("email")).thenReturn(patientDto.getEmail());
        when(request.getParameter("phoneNumber")).thenReturn(patientDto.getPhoneNumber());
        when(request.getParameter("g-recaptcha-response")).thenReturn("{\"success\":true}");
        try(MockedStatic<VerifyRecaptcha> verifier = Mockito.mockStatic(VerifyRecaptcha.class)) {
            verifier.when(() -> VerifyRecaptcha.verify("{\"success\":true}")).thenReturn(true);
            doNothing().when(patientFacade).addPatient(patientDto);
            String returnValue = patientRegistration.execute(request, response);
            verify(patientFacade, times(1)).addPatient(patientDto);
            assertEquals(Path.REDIRECT, returnValue);
        }
    }
}