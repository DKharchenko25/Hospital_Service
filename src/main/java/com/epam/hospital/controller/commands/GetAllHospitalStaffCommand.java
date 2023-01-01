package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.utils.Sorting;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GetAllHospitalStaffCommand implements Command {

    private HospitalStaffFacade hospitalStaffFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedPage = request.getParameter("page");
        String sorting = request.getParameter("sorting");
        int page = 1;
        int recordsPerPage = 6;
        if (requestedPage != null) page = Integer.parseInt(requestedPage);
        int offset = (page - 1) * recordsPerPage;
        String attributeName = "allHospitalStaff";
        if (sorting == null) {
            request.setAttribute(attributeName,
                    hospitalStaffFacade.getAllHospitalStaffPageable(offset, recordsPerPage));
        } else if (sorting.equals(Sorting.BY_CATEGORY.getViewValue())) {
            request.setAttribute(attributeName,
                    hospitalStaffFacade.getAllHospitalStaffSortedByCategory(offset, recordsPerPage));
        } else if (sorting.equals(Sorting.ALPHABETICALLY.getViewValue())) {
            request.setAttribute(attributeName,
                    hospitalStaffFacade.getAllHospitalStaffSortedAlphabetically(offset, recordsPerPage));
        } else {
            request.setAttribute(attributeName,
                    hospitalStaffFacade.getAllHospitalStaffSortedByNumberOfPatients(offset, recordsPerPage));
        }
        int numberOfPages = (int) Math.ceil(hospitalStaffFacade.getNumberOfRows() * 1.0 / recordsPerPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sorting", sorting);
        return Path.ALL_STAFF_PAGE;
    }
}
