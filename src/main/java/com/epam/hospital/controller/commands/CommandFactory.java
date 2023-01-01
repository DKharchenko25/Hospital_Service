package com.epam.hospital.controller.commands;

import com.epam.hospital.facades.FacadeFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
    private static final Map<String, Command> COMMAND_MAP;

    static {
        COMMAND_MAP = new HashMap<>();
        COMMAND_MAP.put("login", new LoginCommand(FacadeFactory.getHospitalStaffFacade(), FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("all_patients", new GetAllPatientsCommand(FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("all_hospital_staff", new GetAllHospitalStaffCommand(FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("add_hospital_card", new AddHospitalCardCommand(FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("appoint_patient", new AppointCommand(FacadeFactory.getPatientFacade(), FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("delete_hospital_staff", new DeleteHospitalStaffCommand(FacadeFactory.getHospitalStaffFacade(), FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("delete_patient", new DeletePatientCommand(FacadeFactory.getPatientFacade(), FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("discharge_patient", new DischargeCommand(FacadeFactory.getPatientFacade(), FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("doctor_cards", new GetDoctorCardsCommand(FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("edit_card", new EditCardCommand(FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("all_doctors_patients", new GetDoctorPatientsCommand(FacadeFactory.getHospitalStaffFacade(), FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("get_hospital_staff", new GetHospitalStaffCommand(FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("all_patients_cards", new GetPatientCardsCommand(FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("all_patients_doctors", new GetPatientDoctorsCommand(FacadeFactory.getPatientFacade(), FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("get_patient", new GetPatientCommand(FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("logout", new LogoutCommand());
        COMMAND_MAP.put("patient_registration", new PatientRegistrationCommand(FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("remove_card", new RemoveCardCommand(FacadeFactory.getHospitalCardFacade()));
        COMMAND_MAP.put("staff_registration", new StaffRegistrationCommand(FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("update_patient", new UpdatePatientCommand(FacadeFactory.getPatientFacade()));
        COMMAND_MAP.put("update_hospital_staff", new UpdateStaffCommand(FacadeFactory.getHospitalStaffFacade()));
        COMMAND_MAP.put("welcome", new WelcomeCommand());
        COMMAND_MAP.put("locale_handler", new LocaleHandlerCommand());
        COMMAND_MAP.put("error_handler", new ErrorHandlerCommand());

    }


    public static Command getCommand(HttpServletRequest request) {
        String commandPattern = request.getParameter("command");
        return COMMAND_MAP.get(commandPattern);
    }
}
