package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.data_access_layer.daos.CategoryDao;
import com.epam.hospital.data_access_layer.daos.CategoryDaoImpl;
import com.epam.hospital.data_access_layer.daos.RoleDao;
import com.epam.hospital.data_access_layer.daos.RoleDaoImpl;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.converters.CategoryConverter;
import com.epam.hospital.facades.converters.RoleConverter;
import com.epam.hospital.facades.dtos.CategoryDto;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.facades.dtos.RoleDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
import com.epam.hospital.utils.VerifyRecaptcha;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class StaffRegistrationCommand implements Command {

    private HospitalStaffFacade hospitalStaffFacade;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("POST")) {
            return executePost(request, response);
        } else {
            return executeGet(request);
        }
    }

    private String executeGet(HttpServletRequest request) {
        RoleDao roleDao = new RoleDaoImpl();
        List<RoleDto> roles = roleDao.findAll().stream().map(RoleConverter::convertRoleToRoleDto).collect(Collectors.toList());
        request.getSession().setAttribute("roles", roles);
        CategoryDao categoryDao = new CategoryDaoImpl();
        List<CategoryDto> categories = categoryDao.findAll().stream().map(CategoryConverter::convertCategoryToCategoryDto).collect(Collectors.toList());
        request.getSession().setAttribute("categories", categories);
        return Path.STAFF_REGISTRATION_PAGE;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String role = request.getParameter("role");
        String category = request.getParameter("category");
        String recaptchaResponse = request.getParameter("g-recaptcha-response");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("isWrongPassword", true);
            return Path.STAFF_REGISTRATION_PAGE;
        }

        if (!VerifyRecaptcha.verify(recaptchaResponse)) {
            request.setAttribute("wrongRecaptcha", true);
            return Path.STAFF_REGISTRATION_PAGE;
        }

        if (StaffInputValidator.isUsernameExists(username) || PatientInputValidator.isUsernameExists(username)) {
            request.setAttribute("existingUsername", true);
            return Path.STAFF_REGISTRATION_PAGE;
        } else {
            HospitalStaffDto dto = new HospitalStaffDto();
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setRole(new RoleDto(0L, role));
            dto.setCategory(new CategoryDto(0L, category));
            hospitalStaffFacade.addHospitalStaff(dto);
            response.sendRedirect(request.getContextPath() + Path.ALL_STAFF_COMMAND);
            return Path.REDIRECT;
        }
    }
}
