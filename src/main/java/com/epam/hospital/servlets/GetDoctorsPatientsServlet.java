package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Doctor's patients", urlPatterns = "/all_doctors_patients")
public class GetDoctorsPatientsServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();
    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        Object user = request.getSession().getAttribute("user");
        HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffById(id);
        request.setAttribute("hospitalStaff", hospitalStaff);
        request.setAttribute("patients", patientFacade.getAllPatientsByHospitalStaffId(id));
        if (user.toString().contains("ADMIN")) {
            request.getRequestDispatcher("getDoctorsPatients.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("getDoctorsPatientsForStaff.jsp").forward(request, response);
        }
    }
}
