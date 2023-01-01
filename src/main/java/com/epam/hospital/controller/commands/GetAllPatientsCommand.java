package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.PatientFacade;
import com.epam.hospital.utils.Sorting;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GetAllPatientsCommand implements Command {

    private PatientFacade patientFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedPage = request.getParameter("page");
        String sorting = request.getParameter("sorting");
        int page = 1;
        int recordsPerPage = 6;
        if (requestedPage != null) page = Integer.parseInt(requestedPage);
        int offset = (page - 1) * recordsPerPage;
        String attributeName = "allPatients";
        if (sorting == null) {
            request.setAttribute(attributeName, patientFacade.getAllPatientsPageable(offset, recordsPerPage));
        } else if (sorting.equals(Sorting.ALPHABETICALLY.getViewValue())) {
            request.setAttribute(attributeName, patientFacade.getAllPatientsSortedAlphabetically(offset, recordsPerPage));
        } else {
            request.setAttribute(attributeName, patientFacade.getAllPatientsSortedByBirthDate(offset, recordsPerPage));
        }
        int numberOfPages = (int) Math.ceil(patientFacade.getNumberOfRows() * 1.0 / recordsPerPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sorting", sorting);
        return Path.ALL_PATIENTS_PAGE;
    }
}
