package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class UpdatePatientCommand implements Command {

    private PatientFacade patientFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            return executePost(request, response);
        } else {
            return executeGet(request);
        }
    }

    private String executeGet(HttpServletRequest request) {
        String id = request.getParameter("id");
        request.setAttribute("patient", patientFacade.getPatientById(Long.valueOf(id)));
        return Path.UPDATE_PATIENT_PAGE;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("newUsername");
        String password = request.getParameter("newPassword");
        String firstName = request.getParameter("newFirstName");
        String lastName = request.getParameter("newLastName");
        String birthDate = request.getParameter("newBirthDate");
        String email = request.getParameter("newEmail");
        String phoneNumber = request.getParameter("newPhoneNumber");

        if (PatientInputValidator.isUsernameExists(username) || StaffInputValidator.isUsernameExists(username)) {
            request.getSession().setAttribute("existingUsername", true);
            response.sendRedirect(request.getContextPath() + Path.getUpdatePatientCommandPath(id));
            return Path.REDIRECT;
        }

        if (username != null && !username.isEmpty()) {
            patientFacade.updatePatientUsernameById(id, username);
        }
        if (password != null && !password.isEmpty()) {
            patientFacade.updatePatientPasswordById(id, password);
        }
        if (firstName != null && !firstName.isEmpty()) {
            patientFacade.updatePatientFirstNameById(id, firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            patientFacade.updatePatientLastNameById(id, lastName);
        }
        if (birthDate != null && !birthDate.isEmpty()) {
            patientFacade.updatePatientBirthDateById(id, birthDate);
        }
        if (email != null && !email.isEmpty()) {
            patientFacade.updatePatientEmailById(id, email);
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            patientFacade.updatePatientPhoneNumberById(id, phoneNumber);
        }

        request.getSession().removeAttribute("existingUsername");
        response.sendRedirect(request.getContextPath() + Path.getPatientCommandPath(id));
        return Path.REDIRECT;
    }
}
