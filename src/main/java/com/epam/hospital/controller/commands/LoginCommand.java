package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
import com.epam.hospital.utils.Roles;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.epam.hospital.facades.validators.PasswordEncoder.getDecodedPassword;

@AllArgsConstructor
public class LoginCommand implements Command {
    private HospitalStaffFacade hospitalStaffFacade;
    private PatientFacade patientFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            return executePost(request, response);
        } else {
            return executeGet();
        }
    }

    private String executeGet() {
        return Path.INDEX;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (role == null) {
            request.setAttribute("isRoleNull", true);
            return Path.INDEX;
        }

        if (Objects.requireNonNull(role).equals(Roles.HOSPITAL_STAFF.toString())) {
            if (StaffInputValidator.isUsernameExists(username)) {
                HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffByUsername(username);
                if (!getDecodedPassword(hospitalStaff.getPassword()).equals(password)) {
                    request.setAttribute("isPasswordWrong", true);
                    return Path.INDEX;
                } else {
                    request.getSession().setAttribute("user", hospitalStaff);
                    request.getSession().setAttribute("role", hospitalStaff.getRole().getName());
                    response.sendRedirect(request.getContextPath() + Path.WELCOME_COMMAND);
                    return Path.REDIRECT;
                }
            } else {
                request.setAttribute("isUsernameWrong", true);
                return Path.INDEX;
            }
        }

        if (Objects.requireNonNull(role).equals(Roles.PATIENT.toString())) {
            if (PatientInputValidator.isUsernameExists(username)) {
                PatientDto patient = patientFacade.getPatientByUsername(username);
                if (!getDecodedPassword(patient.getPassword()).equals(password)) {
                    request.setAttribute("isPasswordWrong", true);
                    return Path.INDEX;
                } else {
                    request.getSession().setAttribute("user", patient);
                    response.sendRedirect(request.getContextPath() + Path.WELCOME_COMMAND);
                    request.getSession().setAttribute("role", Roles.PATIENT.toString());
                    return Path.REDIRECT;
                }
            } else {
                request.setAttribute("isUsernameWrong", true);
                return Path.INDEX;
            }
        }
        return Path.REDIRECT;
    }
}
