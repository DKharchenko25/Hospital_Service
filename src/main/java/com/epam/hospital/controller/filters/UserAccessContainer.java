package com.epam.hospital.controller.filters;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserAccessContainer {

    private static final List<String> COMMON_ACCESS_LIST;
    private static final List<String> ADMIN_ACCESS_LIST;
    private static final List<String> PATIENT_ACCESS_LIST;
    private static final List<String> STAFF_ACCESS_LIST;

    static {
        COMMON_ACCESS_LIST = new ArrayList<>();
        ADMIN_ACCESS_LIST = new ArrayList<>();
        PATIENT_ACCESS_LIST = new ArrayList<>();
        STAFF_ACCESS_LIST = new ArrayList<>();

        COMMON_ACCESS_LIST.add("login");
        COMMON_ACCESS_LIST.add("logout");
        COMMON_ACCESS_LIST.add("welcome");
        COMMON_ACCESS_LIST.add("locale_handler");
        COMMON_ACCESS_LIST.add("error_handler");

        ADMIN_ACCESS_LIST.addAll(COMMON_ACCESS_LIST);
        ADMIN_ACCESS_LIST.add("all_patients");
        ADMIN_ACCESS_LIST.add("all_hospital_staff");
        ADMIN_ACCESS_LIST.add("appoint_patient");
        ADMIN_ACCESS_LIST.add("delete_hospital_staff");
        ADMIN_ACCESS_LIST.add("delete_patient");
        ADMIN_ACCESS_LIST.add("discharge_patient");
        ADMIN_ACCESS_LIST.add("all_doctors_patients");
        ADMIN_ACCESS_LIST.add("get_hospital_staff");
        ADMIN_ACCESS_LIST.add("all_patients_cards");
        ADMIN_ACCESS_LIST.add("all_patients_doctors");
        ADMIN_ACCESS_LIST.add("get_patient");
        ADMIN_ACCESS_LIST.add("patient_registration");
        ADMIN_ACCESS_LIST.add("staff_registration");
        ADMIN_ACCESS_LIST.add("update_patient");
        ADMIN_ACCESS_LIST.add("update_hospital_staff");

        PATIENT_ACCESS_LIST.addAll(COMMON_ACCESS_LIST);
        PATIENT_ACCESS_LIST.add("all_patients_cards");
        PATIENT_ACCESS_LIST.add("all_patients_doctors");
        PATIENT_ACCESS_LIST.add("get_patient");

        STAFF_ACCESS_LIST.addAll(COMMON_ACCESS_LIST);
        STAFF_ACCESS_LIST.add("add_hospital_card");
        STAFF_ACCESS_LIST.add("doctor_cards");
        STAFF_ACCESS_LIST.add("edit_card");
        STAFF_ACCESS_LIST.add("all_doctors_patients");
        STAFF_ACCESS_LIST.add("get_hospital_staff");
        STAFF_ACCESS_LIST.add("remove_card");
    }

    public static boolean checkAccess(HttpServletRequest request) {
        String command = request.getParameter("command");
        String role = request.getSession().getAttribute("role").toString();
        if (role.equals("ADMIN")) {
            Optional<String> optionalAccess = ADMIN_ACCESS_LIST.stream().filter(element -> element.equals(command)).findAny();
            return optionalAccess.isPresent();
        }
        if (role.equals("PATIENT")) {
            Optional<String> optionalAccess = PATIENT_ACCESS_LIST.stream().filter(element -> element.equals(command)).findAny();
            return optionalAccess.isPresent();
        }
        if (role.equals("DOCTOR") || role.equals("NURSE")) {
            Optional<String> optionalAccess = STAFF_ACCESS_LIST.stream().filter(element -> element.equals(command)).findAny();
            return optionalAccess.isPresent();
        }
        return false;
    }
}
