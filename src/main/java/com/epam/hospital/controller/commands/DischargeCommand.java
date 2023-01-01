package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class DischargeCommand implements Command {

    private PatientFacade patientFacade;
    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long patientId = Long.parseLong(request.getParameter("id"));
        patientFacade.dischargePatientById(patientId);
        hospitalCardFacade.writeAndSendCardToPatient(patientId);
        response.sendRedirect(request.getContextPath() + Path.getPatientCommandPath(patientId));
        return Path.REDIRECT;
    }
}
