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

@WebServlet(name = "Hospital Staff Registration", urlPatterns = "/staff_registration")
public class StaffRegistrationServlet extends HttpServlet {

    private final HospitalStaffFacade hospitalStaffFacade = FacadeFactory.getHospitalStaffFacade();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String role = request.getParameter("role");
        String category = request.getParameter("category");
        String path = "registerStaff.jsp";


        if (!password.equals(confirmPassword)) {
            request.setAttribute("isWrongPassword", true);
            request.getRequestDispatcher(path).forward(request, response);
            return;
        }

        if (StaffInputValidator.isUsernameExists(username)) {
            request.setAttribute("existingUsername", true);
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            HospitalStaffDto dto = new HospitalStaffDto();
            dto.setUsername(username);
            dto.setPassword(password);
            dto.setFirstName(firstName);
            dto.setLastName(lastName);
            dto.setRoleName(role);
            dto.setCategoryName(category);
            hospitalStaffFacade.addHospitalStaff(dto);
            response.sendRedirect(request.getContextPath() + "/all_hospital_staff");
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RoleDao roleDao = new RoleDaoImpl();
        List<Role> roles = roleDao.findAll().stream()
                .filter(role -> !role.getName().contains("PATIENT")).collect(Collectors.toList());
        request.getSession().setAttribute("roles", roles);
        CategoryDao categoryDao = new CategoryDaoImpl();
        List<Category> categories = categoryDao.findAll();
        request.getSession().setAttribute("categories", categories);
        request.getRequestDispatcher("registerStaff.jsp").forward(request, response);
    }
}
