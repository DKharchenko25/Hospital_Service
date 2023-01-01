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
public class GetDoctorCardsCommand implements Command {

    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        List<HospitalCardDto> hospitalCardList = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCardDto -> hospitalCardDto.getPatient().getId() == patientId &&
                        hospitalCardDto.getDoctor().getId() == doctorId).collect(Collectors.toList());
        request.setAttribute("cards", hospitalCardList);
        return Path.GET_DOCTOR_CARDS;
    }
}
