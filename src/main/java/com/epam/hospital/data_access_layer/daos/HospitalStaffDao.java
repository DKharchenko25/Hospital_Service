package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.utils.Sorting;

import java.util.List;
import java.util.Optional;

public interface HospitalStaffDao extends Dao<HospitalStaff> {

    Optional<HospitalStaff> findByUsername(String username);

    List<HospitalStaff> findAllPageable(int offset, int numberOfRows);

    List<HospitalStaff> findAllPageableAndSorted(int offset, int numberOfRows, Sorting sortBy);

    List<HospitalStaff> findAllPageableAndSortedByCategory(int offset, int numberOfRows);

    List<HospitalStaff> findAllByPatientId(long id);

    int getNumberOfRows();

}
