package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Remove Card", urlPatterns = "/remove_card")
public class RemoveCardServlet extends HttpServlet {

    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        HospitalCardDto hospitalCard = hospitalCardFacade.getHospitalCardById(id);
        hospitalCardFacade.deleteHospitalCardById(id);
        response.sendRedirect(request.getContextPath() + "/doctor_cards?patientId=" +
                hospitalCard.getPatient().getId() + "&doctorId=" + hospitalCard.getDoctor().getId());
    }
}
