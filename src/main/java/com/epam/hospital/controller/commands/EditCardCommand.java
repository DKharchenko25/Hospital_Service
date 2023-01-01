package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalCardFacade;
import com.epam.hospital.facades.dtos.HospitalCardDto;
import com.epam.hospital.utils.Roles;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class EditCardCommand implements Command {

    private HospitalCardFacade hospitalCardFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            return executePost(request, response);
        } else {
            return executeGet(request);
        }
    }

    private String executeGet(HttpServletRequest request) {
        long id = Long.parseLong(request.getParameter("id"));
        HospitalCardDto hospitalCard = hospitalCardFacade.getHospitalCardById(id);
        request.setAttribute("card", hospitalCard);
        String role = hospitalCard.getDoctor().getRole().getName();
        if (role.equals(Roles.NURSE.toString())) {
            return Path.EDIT_CARD_FOR_NURSE_PAGE;
        } else {
            return Path.EDIT_CARD_FOR_DOCTOR_PAGE;
        }
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        String procedures = request.getParameter("procedures");
        String medications = request.getParameter("medications");
        String operations = request.getParameter("operations");
        String diagnosis = request.getParameter("diagnosis");
        if (procedures != null) {
            hospitalCardFacade.insertRecordToPatientProcedures(id, procedures);
        }
        if (medications != null) {
            hospitalCardFacade.insertRecordToPatientMedications(id, medications);
        }
        if (operations != null) {
            hospitalCardFacade.insertRecordToPatientOperations(id, operations);
        }
        if (diagnosis != null) {
            hospitalCardFacade.insertRecordToPatientDiagnosis(id, diagnosis);
        }

        response.sendRedirect(request.getContextPath() + Path.getDoctorCardsCommandPath(patientId, doctorId));
        return Path.REDIRECT;
    }
}
