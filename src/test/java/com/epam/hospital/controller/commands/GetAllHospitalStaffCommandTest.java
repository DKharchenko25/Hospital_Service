package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllHospitalStaffCommandTest {

    private Command allStaffCommand;

    @Mock
    private HospitalStaffFacade hospitalStaffFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private List<HospitalStaffDto> hospitalStaffDtoList;

    @BeforeEach
    void init() {
        allStaffCommand = new GetAllHospitalStaffCommand(hospitalStaffFacade);
        hospitalStaffDtoList = new ArrayList<>();
    }

    @Test
    void executeByCategorySuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn("byCategory");

        when(hospitalStaffFacade.getAllHospitalStaffSortedByCategory(0, 6)).thenReturn(hospitalStaffDtoList);
        String returnValue = allStaffCommand.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffSortedByCategory(0, 6);
        Assertions.assertEquals(Path.ALL_STAFF_PAGE, returnValue);
    }

    @Test
    void executeAlphabeticallySuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn("alphabetically");

        when(hospitalStaffFacade.getAllHospitalStaffSortedAlphabetically(0, 6)).thenReturn(hospitalStaffDtoList);
        String returnValue = allStaffCommand.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffSortedAlphabetically(0, 6);
        Assertions.assertEquals(Path.ALL_STAFF_PAGE, returnValue);
    }

    @Test
    void executeByNumberOfPatientsSuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn("numberOfPatients");

        when(hospitalStaffFacade.getAllHospitalStaffSortedByNumberOfPatients(0, 6)).thenReturn(hospitalStaffDtoList);
        String returnValue = allStaffCommand.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffSortedByNumberOfPatients(0, 6);
        Assertions.assertEquals(Path.ALL_STAFF_PAGE, returnValue);
    }

    @Test
    void executeNullSortingSuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn(null);

        when(hospitalStaffFacade.getAllHospitalStaffPageable(0, 6)).thenReturn(hospitalStaffDtoList);
        String returnValue = allStaffCommand.execute(request, response);
        verify(hospitalStaffFacade, times(1)).getAllHospitalStaffPageable(0, 6);
        Assertions.assertEquals(Path.ALL_STAFF_PAGE, returnValue);
    }
}