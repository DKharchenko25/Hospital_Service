package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Discharge Patient", urlPatterns = "/discharge_patient")
public class DischargePatientServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();
    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long patientId = Long.parseLong(request.getParameter("id"));
        patientFacade.dischargePatientById(patientId);
        hospitalCardFacade.writeAndSendCardToPatient(patientId, "C:\\Hospital\\card.txt");
        response.sendRedirect(request.getContextPath() + "/get_patient?id=" + patientId);
    }
}
