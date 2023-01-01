package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class AddHospitalCardCommand implements Command {

    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        hospitalCardFacade.addHospitalCard(patientId, doctorId);
        response.sendRedirect(request.getContextPath() + Path.getDoctorCardsCommandPath(patientId, doctorId));
        return Path.REDIRECT;
    }
}
