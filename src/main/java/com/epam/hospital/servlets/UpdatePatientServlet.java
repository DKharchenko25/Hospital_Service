package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.validators.PatientInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Update Patient", urlPatterns = "/update_patient")
public class UpdatePatientServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("newUsername");
        String password = request.getParameter("newPassword");
        String firstName = request.getParameter("newFirstName");
        String lastName = request.getParameter("newLastName");
        String birthDate = request.getParameter("newBirthDate");
        String email = request.getParameter("newEmail");
        String phoneNumber = request.getParameter("newPhoneNumber");

        if (PatientInputValidator.isUsernameExists(username)) {
            request.getSession().setAttribute("existingUsername", true);
            response.sendRedirect(request.getContextPath() + "/update_patient?id=" + id);
            return;
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
        response.sendRedirect(request.getContextPath() + "/get_patient?id=" + id);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("patient", patientFacade.getPatientById(Long.valueOf(id)));
        request.getRequestDispatcher("updatePatient.jsp").forward(request, response);
    }
}
