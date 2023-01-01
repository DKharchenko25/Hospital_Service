package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class RemoveCardCommand implements Command {

    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        HospitalCardDto hospitalCard = hospitalCardFacade.getHospitalCardById(id);
        hospitalCardFacade.deleteHospitalCardById(id);
        response.sendRedirect(request.getContextPath() + Path.getDoctorCardsCommandPath(hospitalCard.getPatient().getId(),
                hospitalCard.getDoctor().getId()));
        return Path.REDIRECT;
    }
}
