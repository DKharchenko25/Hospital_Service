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
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "Appoint Patient", urlPatterns = "/appoint_patient")
public class AppointServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();
    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        long patientId = Long.parseLong(request.getParameter("patientId"));
        patientFacade.appointPatientToDoctor(patientId, doctorId);
        response.sendRedirect(request.getContextPath() + "/get_patient?id=" + patientId);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String patientId = request.getParameter("patientId");
        List<HospitalStaffDto> hospitalStaffList = hospitalStaffFacade.getAllHospitalStaff()
                .stream().filter(hospitalStaffDto -> !hospitalStaffDto.getRole().getName().contains("ADMIN"))
                .collect(Collectors.toList());
        request.setAttribute("patient", patientFacade.getPatientById(Long.valueOf(patientId)));
        request.setAttribute("doctors", hospitalStaffList);
        request.getRequestDispatcher("appoint.jsp").forward(request, response);
    }
}
