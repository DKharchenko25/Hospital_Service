package com.epam.hospital.daos;

import com.epam.hospital.models.HospitalStaff;
import com.epam.hospital.repositories.Sorting;

import java.util.List;
import java.util.Optional;

public interface HospitalStaffDao extends Dao<HospitalStaff> {

    Optional<HospitalStaff> findByUsername(String username);

    List<HospitalStaff> findAllPageable(int offset, int numberOfRows);

    List<HospitalStaff> findAllPageableAndSorted(int offset, int numberOfRows, Sorting sortBy);

    List<HospitalStaff> findAllByPatientId(long id);

    int getNumberOfRows();

}
