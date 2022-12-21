package com.epam.hospital.servlets;

import com.epam.hospital.daos.CategoryDao;
import com.epam.hospital.daos.CategoryDaoImpl;
import com.epam.hospital.daos.RoleDao;
import com.epam.hospital.daos.RoleDaoImpl;
import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.facades.FacadeFactory;
import com.epam.hospital.facades.HospitalStaffFacade;
import com.epam.hospital.models.Category;
import com.epam.hospital.models.Role;
import com.epam.hospital.validators.StaffInputValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@WebServlet(name = "Update Hospital Staff", urlPatterns = "/update_hospital_staff")
public class UpdateStaffServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long id = Long.parseLong(request.getParameter("id"));
        String username = request.getParameter("newUsername");
        String password = request.getParameter("newPassword");
        String firstName = request.getParameter("newFirstName");
        String lastName = request.getParameter("newLastName");
        String role = request.getParameter("newRole");
        String category = request.getParameter("newCategory");

        if (StaffInputValidator.isUsernameExists(username)) {
            request.getSession().setAttribute("existingUsername", true);
            response.sendRedirect(request.getContextPath() + "/update_hospital_staff?id=" + id);
            return;
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
        response.sendRedirect(request.getContextPath() + "/get_hospital_staff?id=" + id);

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        HospitalStaffDto hospitalStaff = hospitalStaffFacade.getHospitalStaffById(Long.valueOf(id));
        request.setAttribute("hospitalStaff", hospitalStaff);
        RoleDao roleDao = new RoleDaoImpl();
        List<Role> roles = roleDao.findAll().stream()
                .filter(role -> !role.getName().contains("PATIENT")).collect(Collectors.toList());
        CategoryDao categoryDao = new CategoryDaoImpl();
        List<Category> categories = categoryDao.findAll();
        request.setAttribute("staffCategories", categories);
        request.setAttribute("staffRoles", roles);
        request.getRequestDispatcher("updateStaff.jsp").forward(request, response);

    }
}
