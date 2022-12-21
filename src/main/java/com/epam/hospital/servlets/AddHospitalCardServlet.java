package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "New Card", urlPatterns = "/add_hospital_card")
public class AddHospitalCardServlet extends HttpServlet {

    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        hospitalCardFacade.addHospitalCard(patientId, doctorId);
        response.sendRedirect(request.getContextPath() + "/doctor_cards?patientId=" + patientId + "&doctorId=" + doctorId);
    }
}
