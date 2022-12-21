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

@WebServlet(name = "Doctor's Cards", urlPatterns = "/doctor_cards")
public class DoctorCardsServlet extends HttpServlet {

    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        List<HospitalCardDto> hospitalCardList = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCardDto -> hospitalCardDto.getPatient().getId() == patientId &&
                        hospitalCardDto.getDoctor().getId() == doctorId).collect(Collectors.toList());
        request.setAttribute("cards", hospitalCardList);
        request.getRequestDispatcher("getDoctorsCards.jsp").forward(request, response);
    }
}
