package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GetPatientCardsCommand implements Command {

    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        List<HospitalCardDto> hospitalCardList = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCardDto -> hospitalCardDto.getPatient().getId() == id)
                .collect(Collectors.toList());
        request.setAttribute("cards", hospitalCardList);
        return Path.GET_PATIENT_CARDS_PAGE;
    }
}
