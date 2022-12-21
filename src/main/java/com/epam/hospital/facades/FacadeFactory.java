package com.epam.hospital.facades;

import com.epam.hospital.daos.*;
import com.epam.hospital.models.HospitalCard;
import com.epam.hospital.repositories.*;
import com.epam.hospital.services.*;


public class FacadeFactory {
    private static final PatientDao PATIENT_DAO = new PatientDaoImpl();
    private static final HospitalStaffDao HOSPITAL_STAFF_DAO = new HospitalStaffDaoImpl();
    private static final Dao<HospitalCard> HOSPITAL_CARD_DAO = new HospitalCardDaoImpl();
    private static final RoleDao ROLE_DAO = new RoleDaoImpl();
    private static final CategoryDao CATEGORY_DAO = new CategoryDaoImpl();
    private static final PatientRepository PATIENT_REPOSITORY;
    private static final HospitalStaffRepository HOSPITAL_STAFF_REPOSITORY;
    private static final HospitalCardRepository HOSPITAL_CARD_REPOSITORY;
    private static final HospitalStaffService HOSPITAL_STAFF_SERVICE;
    private static final PatientService PATIENT_SERVICE;
    private static final HospitalCardService HOSPITAL_CARD_SERVICE;

    private static final EmailService EMAIL_SERVICE = new EmailServiceImpl();
    private static final HospitalCardWriter HOSPITAL_CARD_WRITER = new HospitalCardWriterImpl();


    static {
        PATIENT_REPOSITORY = new PatientRepositoryImpl(PATIENT_DAO, HOSPITAL_STAFF_DAO);
        HOSPITAL_STAFF_REPOSITORY = new HospitalStaffRepositoryImpl(HOSPITAL_STAFF_DAO, ROLE_DAO, CATEGORY_DAO);
        HOSPITAL_CARD_REPOSITORY = new HospitalCardRepositoryImpl(HOSPITAL_CARD_DAO, PATIENT_REPOSITORY, HOSPITAL_STAFF_REPOSITORY);
        HOSPITAL_STAFF_SERVICE = new HospitalStaffServiceImpl(HOSPITAL_STAFF_REPOSITORY);
        PATIENT_SERVICE = new PatientServiceImpl(PATIENT_REPOSITORY, HOSPITAL_STAFF_SERVICE);
        HOSPITAL_CARD_SERVICE = new HospitalCardServiceImpl(HOSPITAL_CARD_REPOSITORY, PATIENT_SERVICE, HOSPITAL_STAFF_SERVICE, EMAIL_SERVICE, HOSPITAL_CARD_WRITER);
    }


    public static PatientFacade getPatientFacade() {
        return new PatientFacadeImpl(PATIENT_SERVICE);
    }

    public static HospitalStaffFacade getHospitalStaffFacade() {
        return new HospitalStaffFacadeImpl(HOSPITAL_STAFF_SERVICE);
    }

    public static HospitalCardFacade getHospitalCardFacade() {
        return new HospitalCardFacadeImpl(HOSPITAL_CARD_SERVICE);
    }
}
