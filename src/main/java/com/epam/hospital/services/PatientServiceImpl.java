package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.data_access_layer.repositories.PatientRepository;
import com.epam.hospital.facades.validators.PasswordEncoder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalStaffService hospitalStaffService;

    public PatientServiceImpl(PatientRepository patientRepository, HospitalStaffService hospitalStaffService) {
        this.patientRepository = patientRepository;
        this.hospitalStaffService = hospitalStaffService;
    }

    @Override
    public void addPatient(@NonNull Patient patient) {
        if (!patientRepository.getPatientByUsername(patient.getUsername()).isPresent()) {
            patient.setPassword(PasswordEncoder.getEncodedPassword(patient.getPassword()));
            patientRepository.addPatient(patient);
        } else {
            String exceptionMessage = "User with username: " + patient.getUsername() + " is already exists";
            log.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    @Override
    public Patient getPatientById(@NonNull Long id) {
        if (patientRepository.getPatientById(id).isPresent()) {
            return patientRepository.getPatientById(id).get();
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    private String getExceptionMessage(long id) {
        return String.format("Patient with ID # %d is not found", id);
    }

    @Override
    public Patient getPatientByUsername(@NonNull String username) {
        if (patientRepository.getPatientByUsername(username).isPresent()) {
            return patientRepository.getPatientByUsername(username).get();
        } else {
            String exceptionMessage = "Patient with username " + username + " is not found";
            log.error(exceptionMessage);
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.getAllPatients();
    }

    @Override
    public List<Patient> getAllPatientsPageable(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientRepository.getAllPatientsPageable(offset, numberOfRows);
    }

    @Override
    public int getNumberOfRows() {
        return patientRepository.getNumberOfRows();
    }

    @Override
    public List<Patient> getAllPatientsByHospitalStaffId(@NonNull Long id) {
        return patientRepository.getAllPatientsByHospitalStaffId(id);
    }


    @Override
    public List<Patient> getAllPatientsSortedAlphabetically(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientRepository.getAllPatientsPageableAndSortedAlphabetically(offset, numberOfRows);
    }

    @Override
    public List<Patient> getAllPatientsSortedByBirthDate(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientRepository.getAllPatientsPageableAndSortedByBirthDate(offset, numberOfRows);
    }

    @Override
    public void appointPatientToDoctor(@NonNull Long patientId, @NonNull Long doctorId) {
        if (patientRepository.getPatientById(patientId).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffService.getHospitalStaffById(doctorId);
            Patient patient = patientRepository.getPatientById(patientId).get();
            if (isAttached(patientId, doctorId)) {
                String exceptionMessage = String.format("Patient %s is already attached to doctor %s",
                        patient.getUsername(), hospitalStaff.getUsername());
                log.error(exceptionMessage);
                throw new IllegalArgumentException(exceptionMessage);
            }
            patientRepository.appointPatientToDoctor(patientId, hospitalStaff.getId());
            hospitalStaffService.updateNumberOfPatientsById(doctorId,
                    hospitalStaff.getNumberOfPatients() + 1);
        } else {
            log.error(getExceptionMessage(patientId));
            throw new IllegalArgumentException(getExceptionMessage(patientId));
        }
    }

    private boolean isAttached(Long patientId, Long doctorId) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffService.getAllHospitalStaffByPatientId(patientId);
        return hospitalStaffList.stream().anyMatch(hospitalStaff -> hospitalStaff.getId() == doctorId);
    }

    @Override
    public void dischargePatientById(@NonNull Long id) {
        if (patientRepository.getPatientById(id).isPresent()) {
            for (HospitalStaff hospitalStaff : hospitalStaffService.getAllHospitalStaffByPatientId(id)) {
                patientRepository.dischargePatientFromDoctor(id, hospitalStaff.getId());
                hospitalStaffService.updateNumberOfPatientsById(hospitalStaff.getId(),
                        hospitalStaff.getNumberOfPatients() - 1);
            }
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void deletePatientById(@NonNull Long id) {
        if (patientRepository.getPatientById(id).isPresent()) {
            dischargePatientById(id);
            patientRepository.deletePatientById(id);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientUsernameById(@NonNull Long id, @NonNull String username) {
        if (!patientRepository.getPatientByUsername(username).isPresent() &&
                patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientUsernameById(id, username);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id) + " Or input username is already exists");
        }
    }

    @Override
    public void updatePatientPasswordById(@NonNull Long id, @NonNull String password) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientPasswordById(id, PasswordEncoder.getEncodedPassword(password));
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientFirstNameById(@NonNull Long id, @NonNull String firstName) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientFirstNameById(id, firstName);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientLastNameById(@NonNull Long id, @NonNull String lastName) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientLastNameById(id, lastName);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientBirthDateById(@NonNull Long id, @NonNull String date) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientBirthDateById(id, date);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientEmailById(@NonNull Long id, @NonNull String email) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientEmailById(id, email);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updatePatientPhoneNumberById(@NonNull Long id, @NonNull String phoneNumber) {
        if (patientRepository.getPatientById(id).isPresent()) {
            patientRepository.updatePatientPhoneNumberById(id, phoneNumber);
        } else {
            log.error(getExceptionMessage(id));
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

}
