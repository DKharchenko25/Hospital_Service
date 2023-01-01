package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DeletePatientCommand implements Command {

    private PatientFacade patientFacade;
    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        List<HospitalCardDto> hospitalCards = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCard -> hospitalCard.getPatient().getId() == id)
                .collect(Collectors.toList());
        hospitalCards.forEach(hospitalCard -> hospitalCardFacade.deleteHospitalCardById(hospitalCard.getId()));
        patientFacade.deletePatientById(id);
        response.sendRedirect(request.getContextPath() + Path.ALL_PATIENTS_COMMAND);
        return Path.REDIRECT;
    }
}
