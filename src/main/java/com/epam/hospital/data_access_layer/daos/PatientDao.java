package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.utils.Sorting;

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
