package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPatientCardsCommandTest {

    private Command getPatientCards;

    @Mock
    private HospitalCardFacade hospitalCardFacade;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private List<HospitalCardDto> hospitalCardDtoList;

    @BeforeEach
    void init() {
        getPatientCards = new GetPatientCardsCommand(hospitalCardFacade);
        hospitalCardDtoList = new ArrayList<>();
    }

    @Test
    void execute() throws ServletException, IOException {
        when(request.getParameter("id")).thenReturn("1");
        when(hospitalCardFacade.getAllHospitalCards()).thenReturn(hospitalCardDtoList);
        String returnValue = getPatientCards.execute(request, response);
        verify(hospitalCardFacade, times(1)).getAllHospitalCards();
        assertEquals(Path.GET_PATIENT_CARDS_PAGE, returnValue);
    }
}