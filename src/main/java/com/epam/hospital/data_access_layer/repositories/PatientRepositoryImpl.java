package com.epam.hospital.data_access_layer.repositories;

import com.epam.hospital.data_access_layer.daos.ReadAndWriteDao;
import com.epam.hospital.data_access_layer.daos.PatientDao;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.utils.Sorting;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
public class PatientRepositoryImpl implements PatientRepository {
    private final PatientDao patientDao;


    private final ReadAndWriteDao<HospitalStaff> hospitalStaffDao;

    public PatientRepositoryImpl(PatientDao patientDao, ReadAndWriteDao<HospitalStaff> hospitalStaffDao) {
        this.patientDao = patientDao;
        this.hospitalStaffDao = hospitalStaffDao;
    }

    @Override
    public void addPatient(Patient patient) {
        patientDao.save(patient);
    }

    @Override
    public Optional<Patient> getPatientById(long id) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            return Optional.of(patient);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Patient> getPatientByUsername(String username) {
        if (patientDao.findByUsername(username).isPresent()) {
            Patient patient = patientDao.findByUsername(username).get();
            return Optional.of(patient);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientDao.findAll();
    }

    @Override
    public List<Patient> getAllPatientsPageable(int offset, int numberOfRows) {
        return patientDao.findAllPageable(offset, numberOfRows);
    }

    @Override
    public List<Patient> getAllPatientsPageableAndSortedAlphabetically(int offset, int numberOfRows) {
        return patientDao.findAllPageableAndSorted(offset, numberOfRows, Sorting.ALPHABETICALLY);
    }

    @Override
    public List<Patient> getAllPatientsPageableAndSortedByBirthDate(int offset, int numberOfRows) {
        return patientDao.findAllPageableAndSorted(offset, numberOfRows, Sorting.BIRTH_DATE);
    }

    @Override
    public int getNumberOfRows() {
        return patientDao.getNumberOfRows();
    }

    @Override
    public List<Patient> getAllPatientsByHospitalStaffId(long id) {
        return patientDao.findAllByHospitalStaffId(id);
    }


    @Override
    public void appointPatientToDoctor(long patientId, long doctorId) {
        Patient patient = patientDao.findById(patientId).orElseThrow(IllegalArgumentException::new);
        HospitalStaff hospitalStaff = hospitalStaffDao.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        patientDao.appointPatientToDoctor(patient.getId(), hospitalStaff.getId());
        log.info("Patient {} is appointed to doctor/nurse {}", patient.getUsername(), hospitalStaff.getUsername());
    }

    @Override
    public void dischargePatientFromDoctor(long patientId, long doctorId) {
        Patient patient = patientDao.findById(patientId).orElseThrow(IllegalArgumentException::new);
        HospitalStaff hospitalStaff = hospitalStaffDao.findById(doctorId).orElseThrow(IllegalArgumentException::new);
        patientDao.dischargePatientFromDoctor(patient.getId(), hospitalStaff.getId());
        log.info("Patient {} is discharged from doctor/nurse {}", patient.getUsername(), hospitalStaff.getUsername());
    }

    @Override
    public void deletePatientById(long id) {
        patientDao.deleteById(id);
    }

    @Override
    public void updatePatientUsernameById(long id, String username) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setUsername(username);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientPasswordById(long id, String password) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setPassword(password);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientFirstNameById(long id, String firstName) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setFirstName(firstName);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientLastNameById(long id, String lastName) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setLastName(lastName);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientBirthDateById(long id, String date) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setBirthDate(date);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientEmailById(long id, String email) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setEmail(email);
            patientDao.update(patient);
        }
    }

    @Override
    public void updatePatientPhoneNumberById(long id, String phoneNumber) {
        if (patientDao.findById(id).isPresent()) {
            Patient patient = patientDao.findById(id).get();
            patient.setPhoneNumber(phoneNumber);
            patientDao.update(patient);
        }
    }
}
