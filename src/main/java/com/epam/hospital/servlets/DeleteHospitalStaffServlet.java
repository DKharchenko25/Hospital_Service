package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.HospitalStaffFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Delete Hospital Staff", urlPatterns = "/delete_hospital_staff")
public class DeleteHospitalStaffServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();
    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        long id = Long.parseLong(request.getParameter("id"));
        List<HospitalCardDto> hospitalCards = hospitalCardFacade.getAllHospitalCards()
                .stream().filter(hospitalCard -> hospitalCard.getDoctor().getId() == id)
                .collect(Collectors.toList());
        hospitalCards.forEach(hospitalCard -> hospitalCardFacade.deleteHospitalCardById(hospitalCard.getId()));
        hospitalStaffFacade.deleteHospitalStaffById(id);
        request.getRequestDispatcher("/all_hospital_staff").forward(request, response);
    }
}
