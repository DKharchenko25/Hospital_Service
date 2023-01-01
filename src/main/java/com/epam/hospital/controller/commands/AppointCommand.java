package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AppointCommand implements Command {

    private PatientFacade patientFacade;
    private HospitalStaffFacade hospitalStaffFacade;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            return executePost(request, response);
        } else {
            return executeGet(request);
        }
    }

    private String executeGet(HttpServletRequest request) {
        String patientId = request.getParameter("patientId");
        List<HospitalStaffDto> hospitalStaffList = hospitalStaffFacade.getAllHospitalStaff()
                .stream().filter(hospitalStaffDto -> !hospitalStaffDto.getRole().getName().contains("ADMIN"))
                .collect(Collectors.toList());
        request.setAttribute("patient", patientFacade.getPatientById(Long.valueOf(patientId)));
        request.setAttribute("doctors", hospitalStaffList);
        return Path.APPOINT_PAGE;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        long patientId = Long.parseLong(request.getParameter("patientId"));
        patientFacade.appointPatientToDoctor(patientId, doctorId);
        response.sendRedirect(request.getContextPath() + Path.getPatientCommandPath(patientId));
        return Path.REDIRECT;
    }
}
