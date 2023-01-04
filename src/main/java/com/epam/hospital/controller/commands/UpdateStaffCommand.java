package com.epam.hospital.controller.commands;

import com.epam.hospital.controller.Path;
import com.epam.hospital.data_access_layer.daos.CategoryDaoImpl;
import com.epam.hospital.data_access_layer.daos.ReadOnlyDao;
import com.epam.hospital.data_access_layer.daos.RoleDaoImpl;
import com.epam.hospital.data_access_layer.models.Category;
import com.epam.hospital.data_access_layer.models.Role;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.facades.converters.CategoryConverter;
import com.epam.hospital.facades.converters.RoleConverter;
import com.epam.hospital.facades.dtos.CategoryDto;
import com.epam.hospital.facades.dtos.HospitalStaffDto;
import com.epam.hospital.facades.dtos.RoleDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.facades.validators.StaffInputValidator;
import lombok.AllArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class UpdateStaffCommand implements Command {

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
        String id = request.getParameter("id");
        HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffById(Long.valueOf(id));
        request.setAttribute("hospitalStaff", hospitalStaff);
        ReadOnlyDao<Role> roleDao = new RoleDaoImpl();
        ReadOnlyDao<Category> categoryDao = new CategoryDaoImpl();
        List<RoleDto> roles = roleDao.findAll().stream()
                .map(RoleConverter::convertRoleToRoleDto).collect(Collectors.toList());
        List<CategoryDto> categories = categoryDao.findAll().stream()
                .map(CategoryConverter::convertCategoryToCategoryDto).collect(Collectors.toList());
        request.setAttribute("staffCategories", categories);
        request.setAttribute("staffRoles", roles);
        return Path.UPDATE_STAFF_PAGE;
    }

    private String executePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("newUsername");
        String password = request.getParameter("newPassword");
        String firstName = request.getParameter("newFirstName");
        String lastName = request.getParameter("newLastName");
        String role = request.getParameter("newRole");
        String category = request.getParameter("newCategory");

        if (StaffInputValidator.isUsernameExists(username) || PatientInputValidator.isUsernameExists(username)) {
            request.getSession().setAttribute("existingUsername", true);
            response.sendRedirect(request.getContextPath() + Path.getUpdateHospitalStaffCommandPath(id));
            return Path.REDIRECT;
        }

        if (username != null && !username.isEmpty()) {
            hospitalStaffFacade.updateStaffUsernameById(id, username);
        }
        if (password != null && !password.isEmpty()) {
            hospitalStaffFacade.updateStaffPasswordById(id, password);
        }
        if (firstName != null && !firstName.isEmpty()) {
            hospitalStaffFacade.updateStaffFirstNameById(id, firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            hospitalStaffFacade.updateStaffLastNameById(id, lastName);
        }
        if (role != null && !role.isEmpty()) {
            hospitalStaffFacade.updateStaffRoleById(id, role);
        }
        if (category != null && !category.isEmpty()) {
            hospitalStaffFacade.updateStaffCategoryById(id, category);
        }
        request.getSession().removeAttribute("existingUsername");
        response.sendRedirect(request.getContextPath() + Path.getHospitalStaffCommandPath(id));
        return Path.REDIRECT;

    }
}
