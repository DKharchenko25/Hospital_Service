package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Patient's doctors", urlPatterns = "/all_patients_doctors")
public class GetPatientDoctorsServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();
    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Object user = request.getSession().getAttribute("user");
        request.setAttribute("patient", patientFacade.getPatientById(id));
        request.setAttribute("doctors", hospitalStaffFacade.getAllHospitalStaffByPatientId(id));
        if (user.toString().contains("ADMIN")) {
            request.getRequestDispatcher("getPatientsDoctors.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("getPatientsDoctorsForPatient.jsp").forward(request, response);
        }
    }
}
