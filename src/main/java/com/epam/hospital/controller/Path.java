package com.epam.hospital.controller;

public class Path {
    public static final String REDIRECT = "redirect";
    public static final String INDEX = "index.jsp";
    public static final String ALL_PATIENTS_PAGE = "allPatients.jsp";
    public static final String ALL_STAFF_PAGE = "allHospitalStaff.jsp";

    public static final String APPOINT_PAGE = "appoint.jsp";
    public static final String ALL_STAFF_COMMAND = "/controller?command=all_hospital_staff";

    public static final String ALL_PATIENTS_COMMAND = "/controller?command=all_patients";

    public static final String GET_DOCTOR_CARDS = "getDoctorsCards.jsp";

    public static final String EDIT_CARD_FOR_NURSE_PAGE = "editCardForNurse.jsp";

    public static final String EDIT_CARD_FOR_DOCTOR_PAGE = "editCardForDoctor.jsp";

    public static final String GET_DOCTOR_PATIENTS_PAGE = "getDoctorsPatients.jsp";

    public static final String GET_DOCTOR_PATIENTS_FOR_STAFF_PAGE = "getDoctorsPatientsForStaff.jsp";

    public static final String GET_HOSPITAL_STAFF_PAGE = "getHospitalStaff.jsp";

    public static final String GET_PATIENT_CARDS_PAGE = "getPatientsCards.jsp";
    public static final String GET_PATIENT_DOCTORS_PAGE = "getPatientsDoctors.jsp";

    public static final String GET_PATIENT_DOCTORS_FOR_PATIENT_PAGE = "getPatientsDoctorsForPatient.jsp";

    public static final String GET_PATIENT_PAGE = "getPatient.jsp";

    public static final String LOGIN_COMMAND = "/controller?command=login";

    public static final String REGISTER_PATIENT_PAGE = "registerPatient.jsp";

    public static final String STAFF_REGISTRATION_PAGE = "registerStaff.jsp";

    public static final String UPDATE_PATIENT_PAGE = "updatePatient.jsp";

    public static final String UPDATE_STAFF_PAGE = "updateStaff.jsp";

    public static final String ADMIN_WELCOME_PAGE = "adminWelcomePage.jsp";

    public static final String PATIENT_WELCOME_PAGE = "patientWelcomePage.jsp";

    public static final String STAFF_WELCOME_PAGE = "staffWelcomePage.jsp";

    public static final String WELCOME_COMMAND = "/controller?command=welcome";

    public static final String ERROR_PAGE = "error.jsp";


    public static String getPatientCommandPath(long id) {
        return String.format("/controller?command=get_patient&id=%d", id);
    }

    public static String getUpdatePatientCommandPath(long id) {
        return String.format("/controller?command=update_patient&id=%d", id);
    }

    public static String getHospitalStaffCommandPath(long id) {
        return String.format("/controller?command=get_hospital_staff&id=%d", id);
    }

    public static String getUpdateHospitalStaffCommandPath(long id) {
        return String.format("/controller?command=update_hospital_staff&id=%d", id);
    }

    public static String getDoctorCardsCommandPath(long patientId, long doctorId) {
        return String.format("/controller?command=doctor_cards&patientId=%d&doctorId=%d", patientId, doctorId);

    }
}
