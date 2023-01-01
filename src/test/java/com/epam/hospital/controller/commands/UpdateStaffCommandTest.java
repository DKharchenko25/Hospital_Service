package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.dtos.CategoryDto;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.facades.dtos.RoleDto;
import com.epam.hospital.facades.validators.StaffInputValidator;
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
class UpdateStaffCommandTest {

    private Command updateStaff;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private HospitalStaffDto hospitalStaffDto;

    @BeforeEach
    void init() {
        updateStaff = new UpdateStaffCommand(hospitalStaffFacade);
        hospitalStaffDto = new HospitalStaffDto();
        hospitalStaffDto.setId(2L);
        hospitalStaffDto.setUsername("Username1");
        hospitalStaffDto.setPassword("TestPassword@123");
        hospitalStaffDto.setFirstName("John");
        hospitalStaffDto.setLastName("Dow");
        hospitalStaffDto.setRole(new RoleDto("DOCTOR"));
        hospitalStaffDto.setCategory(new CategoryDto("Oncologist"));
    }

    @Test
    void executeGetSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("GET");
        when(request.getParameter("id")).thenReturn("2");
        when(hospitalStaffFacade.getHospitalStaffById(2L)).thenReturn(hospitalStaffDto);
        String returnValue = updateStaff.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getHospitalStaffById(2L);
        assertEquals(Path.UPDATE_STAFF_PAGE, returnValue);
    }

    @Test
    void executePostSuccess() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("newUsername")).thenReturn(hospitalStaffDto.getUsername());
        when(request.getParameter("newPassword")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("newFirstName")).thenReturn(hospitalStaffDto.getFirstName());
        when(request.getParameter("newLastName")).thenReturn(hospitalStaffDto.getLastName());
        when(request.getParameter("newRole")).thenReturn(hospitalStaffDto.getRole().getName());
        when(request.getParameter("newCategory")).thenReturn(hospitalStaffDto.getCategory().getName());
        when(request.getSession()).thenReturn(session);

        String returnValue = updateStaff.execute(request, response);

        verify(hospitalStaffFacade, times(1)).updateStaffUsernameById(hospitalStaffDto.getId(), hospitalStaffDto.getUsername());
        verify(hospitalStaffFacade, times(1)).updateStaffPasswordById(hospitalStaffDto.getId(), hospitalStaffDto.getPassword());
        verify(hospitalStaffFacade, times(1)).updateStaffFirstNameById(hospitalStaffDto.getId(), hospitalStaffDto.getFirstName());
        verify(hospitalStaffFacade, times(1)).updateStaffRoleById(hospitalStaffDto.getId(), hospitalStaffDto.getRole().getName());
        verify(hospitalStaffFacade, times(1)).updateStaffCategoryById(hospitalStaffDto.getId(), hospitalStaffDto.getCategory().getName());
        assertEquals(Path.REDIRECT, returnValue);
    }

    @Test
    void executePostWrongUsername() throws ServletException, IOException {
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("id")).thenReturn("2");
        when(request.getParameter("newUsername")).thenReturn(hospitalStaffDto.getUsername());
        when(request.getParameter("newPassword")).thenReturn(hospitalStaffDto.getPassword());
        when(request.getParameter("newFirstName")).thenReturn(hospitalStaffDto.getFirstName());
        when(request.getParameter("newLastName")).thenReturn(hospitalStaffDto.getLastName());
        when(request.getParameter("newRole")).thenReturn(hospitalStaffDto.getRole().getName());
        when(request.getParameter("newCategory")).thenReturn(hospitalStaffDto.getCategory().getName());
        when(request.getSession()).thenReturn(session);

        try (MockedStatic<StaffInputValidator> validator = mockStatic(StaffInputValidator.class)) {
            validator.when(() -> StaffInputValidator.isUsernameExists(hospitalStaffDto.getUsername())).thenReturn(true);
        }

        String returnValue = updateStaff.execute(request, response);
        assertEquals(Path.REDIRECT, returnValue);
    }
}