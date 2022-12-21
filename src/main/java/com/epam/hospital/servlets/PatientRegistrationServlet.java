package com.epam.hospital.servlets;

import com.epam.hospital.dtos.PatientDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.validators.PatientInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Patient Registration", urlPatterns = "/patient_registration")
public class PatientRegistrationServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String path = "registerPatient.jsp";

        if (!password.equals(confirmPassword)) {
            request.setAttribute("isWrongPassword", true);
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }

        if (PatientInputValidator.isUsernameExists(username)) {
            request.setAttribute("existingUsername", true);
            request.getRequestDispatcher(path).forward(request, response);
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
            response.sendRedirect(request.getContextPath() + "/all_patients");
        }
    }

}
