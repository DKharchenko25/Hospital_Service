package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Delete Patient", urlPatterns = "/delete_patient")
public class DeletePatientServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();
    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        List<HospitalCardDto> hospitalCards = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCard -> hospitalCard.getPatient().getId() == id)
                .collect(Collectors.toList());
        hospitalCards.forEach(hospitalCard -> hospitalCardFacade.deleteHospitalCardById(hospitalCard.getId()));
        patientFacade.deletePatientById(id);
        request.getRequestDispatcher("/all_patients").forward(request, response);

    }
}
