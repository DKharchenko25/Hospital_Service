package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Get Hospital Staff", urlPatterns = "/get_hospital_staff")
public class GetHospitalStaffServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("hospitalStaff", hospitalStaffFacade.getHospitalStaffById(Long.valueOf(id)));
        request.getRequestDispatcher("getHospitalStaff.jsp").forward(request, response);
    }
}
