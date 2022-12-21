package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Patient's Cards", urlPatterns = "/all_patients_cards")
public class GetPatientCardsServlet extends HttpServlet {

    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        List<HospitalCardDto> hospitalCardList = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCardDto -> hospitalCardDto.getPatient().getId() == id)
                .collect(Collectors.toList());
        request.setAttribute("cards", hospitalCardList);
        if (request.getSession().getAttribute("user").toString().contains("ADMIN")) {
            request.getRequestDispatcher("getPatientsCards.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("getPatientsCardsForPatient.jsp").forward(request, response);
        }
    }
}
