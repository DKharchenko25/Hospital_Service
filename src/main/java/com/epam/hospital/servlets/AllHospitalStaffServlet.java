package com.epam.hospital.servlets;

import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "All Hospital Staff", urlPatterns = "/all_hospital_staff")
public class AllHospitalStaffServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedPage = request.getParameter("page");
        String sorting = request.getParameter("sorting");
        int page = 1;
        int recordsPerPage = 6;
        if (requestedPage != null) page = Integer.parseInt(requestedPage);
        int offset = (page - 1) * recordsPerPage;
        if (sorting == null) {
            request.setAttribute("allHospitalStaff",
                    hospitalStaffFacade.getAllHospitalStaffPageable(offset, recordsPerPage));
        } else if (sorting.equals("byCategory")) {
            request.setAttribute("allHospitalStaff",
                    hospitalStaffFacade.getAllHospitalStaffSortedByCategory(offset, recordsPerPage));
        } else if (sorting.equals("alphabetically")) {
            request.setAttribute("allHospitalStaff",
                    hospitalStaffFacade.getAllHospitalStaffSortedAlphabetically(offset, recordsPerPage));
        } else {
            request.setAttribute("allHospitalStaff",
                    hospitalStaffFacade.getAllHospitalStaffSortedByNumberOfPatients(offset, recordsPerPage));
        }
        int numberOfPages = (int) Math.ceil(hospitalStaffFacade.getNumberOfRows() * 1.0 / recordsPerPage);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("sorting", sorting);
        request.getRequestDispatcher("allHospitalStaff.jsp").forward(request, response);
    }
}
