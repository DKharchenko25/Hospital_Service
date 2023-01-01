package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetHospitalStaffCommandTest {

    private Command getHospitalStaff;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private HospitalStaffDto hospitalStaffDto;

    @BeforeEach
    void init() {
        getHospitalStaff = new GetHospitalStaffCommand(hospitalStaffFacade);
        hospitalStaffDto = new HospitalStaffDto();
        hospitalStaffDto.setId(1L);
    }

    @Test
    void executeSuccess() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(hospitalStaffFacade.getHospitalStaffById(1L)).thenReturn(hospitalStaffDto);
        String returnValue = getHospitalStaff.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getHospitalStaffById(1L);
        assertEquals(Path.GET_HOSPITAL_STAFF_PAGE, returnValue);
    }
}