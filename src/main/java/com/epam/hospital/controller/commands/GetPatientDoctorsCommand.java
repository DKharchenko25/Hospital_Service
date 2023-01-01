package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.utils.Roles;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GetPatientDoctorsCommand implements Command {
    private PatientFacade patientFacade;
    private HospitalStaffFacade hospitalStaffFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String role = request.getSession().getAttribute("role").toString();
        request.setAttribute("patient", patientFacade.getPatientById(id));
        request.setAttribute("doctors", hospitalStaffFacade.getAllHospitalStaffByPatientId(id));
        if (role.equals(Roles.ADMIN.toString())) {
            return Path.GET_PATIENT_DOCTORS_PAGE;
        } else {
            return Path.GET_PATIENT_DOCTORS_FOR_PATIENT_PAGE;
        }
    }
}
