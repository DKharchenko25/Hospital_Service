package com.epam.hospital.facades;

import com.epam.hospital.facades.dtos.PatientDto;

import java.util.List;

public interface PatientFacade {
    void addPatient(PatientDto dto);

    PatientDto getPatientById(Long id);

    PatientDto getPatientByUsername(String username);

    List<PatientDto> getAllPatientsByHospitalStaffId(Long id);

    List<PatientDto> getAllPatients();

    List<PatientDto> getAllPatientsPageable(Integer offset, Integer numberOfRows);

    int getNumberOfRows();

    List<PatientDto> getAllPatientsSortedAlphabetically(Integer offset, Integer numberOfRows);

    List<PatientDto> getAllPatientsSortedByBirthDate(Integer offset, Integer numberOfRows);

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
