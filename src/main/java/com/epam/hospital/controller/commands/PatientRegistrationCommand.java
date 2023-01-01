package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
import com.epam.hospital.utils.VerifyRecaptcha;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class PatientRegistrationCommand implements Command {

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
        return Path.REGISTER_PATIENT_PAGE;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String recaptchaResponse = request.getParameter("g-recaptcha-response");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("isWrongPassword", true);
            return Path.REGISTER_PATIENT_PAGE;
        }

        if (!VerifyRecaptcha.verify(recaptchaResponse)) {
            request.setAttribute("wrongRecaptcha", true);
            return Path.REGISTER_PATIENT_PAGE;
        }

        if (PatientInputValidator.isUsernameExists(username) || StaffInputValidator.isUsernameExists(username)) {
            request.setAttribute("existingUsername", true);
            return Path.REGISTER_PATIENT_PAGE;
        } else {
            PatientDto dto = new PatientDto();
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setBirthDate(birthDate);
            dto.setEmail(email);
            dto.setPhoneNumber(phoneNumber);
            patientFacade.addPatient(dto);
            response.sendRedirect(request.getContextPath() + Path.ALL_PATIENTS_COMMAND);
            return Path.REDIRECT;
        }
    }
}

