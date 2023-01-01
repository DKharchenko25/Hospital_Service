package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.dtos.CategoryDto;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.dtos.RoleDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
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
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StaffRegistrationCommandTest {

    private Command staffRegistration;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private HospitalStaffDto hospitalStaffDto;

    private String confirmPassword;

    @BeforeEach
    void init() {
        staffRegistration = new StaffRegistrationCommand(hospitalStaffFacade);
        hospitalStaffDto = new HospitalStaffDto();
        hospitalStaffDto.setUsername("Username1");
        hospitalStaffDto.setPassword("TestPassword@123");
        hospitalStaffDto.setFirstName("John");
        hospitalStaffDto.setLastName("Dow");
        hospitalStaffDto.setRole(new RoleDto("DOCTOR"));
        hospitalStaffDto.setCategory(new CategoryDto("Oncologist"));
        confirmPassword = hospitalStaffDto.getPassword();
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession()).thenReturn(session);
        String returnValue = staffRegistration.execute(request, response);
        assertEquals(Path.STAFF_REGISTRATION_PAGE, returnValue);
    }

    @Test
    void executePostWrongPassword() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(hospitalStaffDto.getUsername());
        when(request.getParameter("password")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn("WrongPassword@123");
        when(request.getParameter("firstName")).thenReturn(hospitalStaffDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(hospitalStaffDto.getLastName());
        when(request.getParameter("role")).thenReturn(hospitalStaffDto.getRole().getName());
        when(request.getParameter("category")).thenReturn(hospitalStaffDto.getCategory().getName());

        String returnValue = staffRegistration.execute(request, response);
        assertEquals(Path.STAFF_REGISTRATION_PAGE, returnValue);
    }

    @Test
    void executePostWrongUsername() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(hospitalStaffDto.getUsername());
        when(request.getParameter("password")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("firstName")).thenReturn(hospitalStaffDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(hospitalStaffDto.getLastName());
        when(request.getParameter("role")).thenReturn(hospitalStaffDto.getRole().getName());
        when(request.getParameter("category")).thenReturn(hospitalStaffDto.getCategory().getName());
        try (MockedStatic<StaffInputValidator> validator = Mockito.mockStatic(StaffInputValidator.class)) {
            validator.when(() -> StaffInputValidator.isUsernameExists(hospitalStaffDto.getUsername())).thenReturn(true);
            String returnValue = staffRegistration.execute(request, response);
            assertEquals(Path.STAFF_REGISTRATION_PAGE, returnValue);
        }
    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("username")).thenReturn(hospitalStaffDto.getUsername());
        when(request.getParameter("password")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("confirmPassword")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("firstName")).thenReturn(hospitalStaffDto.getFirstName());
        when(request.getParameter("lastName")).thenReturn(hospitalStaffDto.getLastName());
        when(request.getParameter("role")).thenReturn("DOCTOR");
        when(request.getParameter("category")).thenReturn("Oncologist");
        when(request.getParameter("g-recaptcha-response")).thenReturn("{\"success\":true}");
        try(MockedStatic<VerifyRecaptcha> verifier = Mockito.mockStatic(VerifyRecaptcha.class)) {
            verifier.when(() -> VerifyRecaptcha.verify("{\"success\":true}")).thenReturn(true);
            doNothing().when(hospitalStaffFacade).addHospitalStaff(any(HospitalStaffDto.class));
            String returnValue = staffRegistration.execute(request, response);
            verify(hospitalStaffFacade, times(1)).addHospitalStaff(any(HospitalStaffDto.class));
            assertEquals(Path.REDIRECT, returnValue);
        }
    }
}