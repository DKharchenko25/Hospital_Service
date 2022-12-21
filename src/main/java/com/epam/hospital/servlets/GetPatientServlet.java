package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Get Patient", urlPatterns = "/get_patient")
public class GetPatientServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("patient", patientFacade.getPatientById(Long.valueOf(id)));
        request.getRequestDispatcher("getPatient.jsp").forward(request, response);
    }
}
