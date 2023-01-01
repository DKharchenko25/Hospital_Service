package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.Patient;

import java.util.List;

public interface PatientService {

    void addPatient(Patient patient);

    Patient getPatientById(Long id);

    Patient getPatientByUsername(String username);

    List<Patient> getAllPatients();

    List<Patient> getAllPatientsPageable(Integer offset, Integer numberOfRows);

    int getNumberOfRows();

    List<Patient> getAllPatientsByHospitalStaffId(Long id);

    List<Patient> getAllPatientsSortedAlphabetically(Integer offset, Integer numberOfRows);

    List<Patient> getAllPatientsSortedByBirthDate(Integer offset, Integer numberOfRows);

    void appointPatientToDoctor(Long patientId, Long doctorId);

    void dischargePatientById(Long id);

    void deletePatientById(Long id);

    void updatePatientUsernameById(Long id, String username);

    void updatePatientPasswordById(Long id, String password);

    void updatePatientFirstNameById(Long id, String firstName);

    void updatePatientLastNameById(Long id, String lastName);

    void updatePatientBirthDateById(Long id, String date);

    void updatePatientEmailById(Long id, String email);

    void updatePatientPhoneNumberById(Long id, String phoneNumber);


}
