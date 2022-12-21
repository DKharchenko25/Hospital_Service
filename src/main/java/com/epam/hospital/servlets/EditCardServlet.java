package com.epam.hospital.servlets;

import com.epam.hospital.dtos.HospitalCardDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalCardFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Edit Card", urlPatterns = "/edit_card")
public class EditCardServlet extends HttpServlet {

    private final HospitalCardFacade hospitalCardFacade = FacadeFactory.getHospitalCardFacade();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        long patientId = Long.parseLong(request.getParameter("patientId"));
        long doctorId = Long.parseLong(request.getParameter("doctorId"));
        String procedures = request.getParameter("procedures");
        String medications = request.getParameter("medications");
        String operations = request.getParameter("operations");
        String diagnosis = request.getParameter("diagnosis");

        if (procedures != null && !procedures.isEmpty()) {
            hospitalCardFacade.insertRecordToPatientProcedures(id, procedures);
        }
        if (medications != null && !medications.isEmpty()) {
            hospitalCardFacade.insertRecordToPatientMedications(id, medications);
        }
        if (operations != null && !operations.isEmpty()) {
            hospitalCardFacade.insertRecordToPatientOperations(id, operations);
        }
        if (diagnosis != null && !diagnosis.isEmpty()) {
            hospitalCardFacade.insertRecordToPatientDiagnosis(id, diagnosis);
        }

        response.sendRedirect(request.getContextPath() + "/doctor_cards?patientId=" + patientId
                + "&doctorId=" + doctorId);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
        HospitalCardDto hospitalCard = hospitalCardFacade.getHospitalCardById(id);
        request.setAttribute("card", hospitalCard);
        if (hospitalCard.getDoctor().getRole().getName().equals("NURSE")) {
            request.getRequestDispatcher("editCardForNurse.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("editCardForDoctor.jsp").forward(request, response);
        }
    }
}
