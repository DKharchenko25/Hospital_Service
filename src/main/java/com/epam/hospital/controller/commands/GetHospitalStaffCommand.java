package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.facades.HospitalStaffFacade;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class GetHospitalStaffCommand implements Command {

    private HospitalStaffFacade hospitalStaffFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        request.setAttribute("hospitalStaff", hospitalStaffFacade.getHospitalStaffById(Long.valueOf(id)));
        return Path.GET_HOSPITAL_STAFF_PAGE;
    }
}
