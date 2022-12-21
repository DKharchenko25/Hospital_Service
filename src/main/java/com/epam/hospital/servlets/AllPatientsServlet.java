package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.PatientFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "All Patients", urlPatterns = "/all_patients")
public class AllPatientsServlet extends HttpServlet {

    private final PatientFacade patientFacade = FacadeFactory.getPatientFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedPage = request.getParameter("page");
        String sorting = request.getParameter("sorting");
        int page = 1;
        int recordsPerPage = 6;
        if (requestedPage != null) page = Integer.parseInt(requestedPage);
        int offset = (page - 1) * recordsPerPage;
        if (sorting == null) {
            request.setAttribute("allPatients", patientFacade.getAllPatientsPageable(offset, recordsPerPage));
        } else if (sorting.equals("alphabetically")) {
            request.setAttribute("allPatients", patientFacade.getAllPatientsSortedAlphabetically(offset, recordsPerPage));
        } else {
            request.setAttribute("allPatients", patientFacade.getAllPatientsSortedByBirthDate(offset, recordsPerPage));
        }
        int numberOfPages = (int) Math.ceil(patientFacade.getNumberOfRows() * 1.0 / recordsPerPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sorting", sorting);
        request.getRequestDispatcher("allPatients.jsp").forward(request, response);
    }
}
