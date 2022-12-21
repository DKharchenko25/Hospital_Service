package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.dtos.PatientDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.validators.PatientInputValidator;
import com.epam.hospital.validators.StaffInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.epam.hospital.validators.LoginValidator.*;
import static com.epam.hospital.validators.PasswordEncoder.getDecodedPassword;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();
    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (role == null) {
            nullRoleAction(request, response);
            return;
        }

        if (Objects.requireNonNull(role).equals("HOSPITAL_STAFF")) {
            if (StaffInputValidator.isUsernameExists(username)) {
                HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffByUsername(username);
                if (!getDecodedPassword(hospitalStaff.getPassword()).equals(password)) {
                    wrongPasswordAction(request, response);
                } else {
                    request.getSession().setAttribute("user", hospitalStaff);
                    response.sendRedirect(request.getContextPath() + "/welcome");
                }
            } else {
                wrongUsernameAction(request, response);
            }
        }

        if (Objects.requireNonNull(role).equals("PATIENT")) {
            if (PatientInputValidator.isUsernameExists(username)) {
                PatientDto patient = patientFacade.getPatientByUsername(username);
                if (!getDecodedPassword(patient.getPassword()).equals(password)) {
                    wrongPasswordAction(request, response);
                } else {
                    request.getSession().setAttribute("user", patient);
                    response.sendRedirect(request.getContextPath() + "/welcome");
                }
            } else {
                wrongUsernameAction(request, response);
            }
        }
    }
}
