package com.epam.hospital.repositories;

import com.epam.hospital.models.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    void addPatient(Patient patient);

    Optional<Patient> getPatientById(long id);

    Optional<Patient> getPatientByUsername(String username);

    List<Patient> getAllPatients();

    List<Patient> getAllPatientsPageable(int offset, int numberOfRows);

    List<Patient> getAllPatientsPageableAndSortedAlphabetically(int offset, int numberOfRows);

    List<Patient> getAllPatientsPageableAndSortedByBirthDate(int offset, int numberOfRows);

    int getNumberOfRows();

    List<Patient> getAllPatientsByHospitalStaffId(long id);

    void appointPatientToDoctor(long patientId, long doctorId);

    void dischargePatientFromDoctor(long patientId, long doctorId);

    void deletePatientById(long id);

    void updatePatientUsernameById(long id, String username);

    void updatePatientPasswordById(long id, String password);

    void updatePatientFirstNameById(long id, String firstName);

    void updatePatientLastNameById(long id, String lastName);

    void updatePatientBirthDateById(long id, String date);

    void updatePatientEmailById(long id, String email);

    void updatePatientPhoneNumberById(long id, String phoneNumber);


}
