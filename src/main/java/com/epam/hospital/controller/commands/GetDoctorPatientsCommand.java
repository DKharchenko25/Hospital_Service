package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.utils.Roles;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GetDoctorPatientsCommand implements Command {
    private HospitalStaffFacade hospitalStaffFacade;
    private PatientFacade patientFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String role = request.getSession().getAttribute("role").toString();
        HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffById(id);
        request.setAttribute("hospitalStaff", hospitalStaff);
        request.setAttribute("patients", patientFacade.getAllPatientsByHospitalStaffId(id));
        if (role.equals(Roles.ADMIN.toString())) {
            return Path.GET_DOCTOR_PATIENTS_PAGE;
        } else {
            return Path.GET_DOCTOR_PATIENTS_FOR_STAFF_PAGE;
        }
    }
}
