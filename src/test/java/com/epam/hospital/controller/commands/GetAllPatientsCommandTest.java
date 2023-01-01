package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetAllPatientsCommandTest {

    private Command allPatientsCommand;

    @Mock
    private PatientFacade patientFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private List<PatientDto> patientDtoList;

    @BeforeEach
    void init() {
        allPatientsCommand = new GetAllPatientsCommand(patientFacade);
        patientDtoList = new ArrayList<>();
    }

    @Test
    void executeAlphabeticallySuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn("alphabetically");

        when(patientFacade.getAllPatientsSortedAlphabetically(0, 6)).thenReturn(patientDtoList);
        String returnValue = allPatientsCommand.execute(request, response);
        verify(patientFacade, times(1)).getAllPatientsSortedAlphabetically(0, 6);
        Assertions.assertEquals(Path.ALL_PATIENTS_PAGE, returnValue);
    }

    @Test
    void executeByBirthDateSuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn("byBirthDate");

        when(patientFacade.getAllPatientsSortedByBirthDate(0, 6)).thenReturn(patientDtoList);
        String returnValue = allPatientsCommand.execute(request, response);
        verify(patientFacade, times(1)).getAllPatientsSortedByBirthDate(0, 6);
        Assertions.assertEquals(Path.ALL_PATIENTS_PAGE, returnValue);
    }

    @Test
    void executeNullSortingSuccess() throws ServletException, IOException {
        when(request.getParameter("page")).thenReturn("1");
        when(request.getParameter("sorting")).thenReturn(null);

        when(patientFacade.getAllPatientsPageable(0, 6)).thenReturn(patientDtoList);
        String returnValue = allPatientsCommand.execute(request, response);
        verify(patientFacade, times(1)).getAllPatientsPageable(0, 6);
        Assertions.assertEquals(Path.ALL_PATIENTS_PAGE, returnValue);
    }
}