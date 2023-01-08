package com.epam.hospital.controller.commands;

import com.epam.hospital.facades.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> COMMAND_MAP;

    static {
        COMMAND_MAP = new HashMap<>();
        COMMAND_MAP.put("login", new LoginCommand(ApplicationContext.getHospitalStaffFacade(),
                ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("all_patients", new GetAllPatientsCommand(ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("all_hospital_staff", new GetAllHospitalStaffCommand(ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("add_hospital_card", new AddHospitalCardCommand(ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("appoint_patient", new AppointCommand(ApplicationContext.getPatientFacade(),
                ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("delete_hospital_staff", new DeleteHospitalStaffCommand(ApplicationContext.getHospitalStaffFacade(),
                ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("delete_patient", new DeletePatientCommand(ApplicationContext.getPatientFacade(),
                ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("discharge_patient", new DischargeCommand(ApplicationContext.getPatientFacade(),
                ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("doctor_cards", new GetDoctorCardsCommand(ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("edit_card", new EditCardCommand(ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("all_doctors_patients", new GetDoctorPatientsCommand(ApplicationContext.getHospitalStaffFacade(),
                ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("get_hospital_staff", new GetHospitalStaffCommand(ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("all_patients_cards", new GetPatientCardsCommand(ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("all_patients_doctors", new GetPatientDoctorsCommand(ApplicationContext.getPatientFacade(),
                ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("get_patient", new GetPatientCommand(ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("logout", new LogoutCommand());
        COMMAND_MAP.put("patient_registration", new PatientRegistrationCommand(ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("remove_card", new RemoveCardCommand(ApplicationContext.getHospitalCardFacade()));
        COMMAND_MAP.put("staff_registration", new StaffRegistrationCommand(ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("update_patient", new UpdatePatientCommand(ApplicationContext.getPatientFacade()));
        COMMAND_MAP.put("update_hospital_staff", new UpdateStaffCommand(ApplicationContext.getHospitalStaffFacade()));
        COMMAND_MAP.put("welcome", new WelcomeCommand());
        COMMAND_MAP.put("locale_handler", new LocaleHandlerCommand());
        COMMAND_MAP.put("error_handler", new ErrorHandlerCommand());

    }


    public static Command getCommand(HttpServletRequest request) {
        String commandPattern = request.getParameter("command");
        return COMMAND_MAP.get(commandPattern);
    }
}
