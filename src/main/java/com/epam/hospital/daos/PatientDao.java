package com.epam.hospital.daos;

import com.epam.hospital.models.Patient;
import com.epam.hospital.repositories.Sorting;

import java.util.List;
import java.util.Optional;

public interface PatientDao extends Dao<Patient> {

    Optional<Patient> findByUsername(String username);

    List<Patient> findAllPageable(int offset, int numberOfRows);

    List<Patient> findAllPageableAndSorted(int offset, int numberOfRows, Sorting sortBy);

    List<Patient> findAllByHospitalStaffId(long id);

    int getNumberOfRows();

    void appointPatientToDoctor(long patientId, long doctorId);

    void dischargePatientFromDoctor(long patientId, long doctorId);

}
