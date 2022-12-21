package com.epam.hospital.repositories;

import com.epam.hospital.models.HospitalStaff;

import java.util.List;
import java.util.Optional;

public interface HospitalStaffRepository {

    void addStaff(HospitalStaff hospitalStaff);

    Optional<HospitalStaff> getHospitalStaffById(long id);

    Optional<HospitalStaff> getHospitalStaffByUsername(String username);

    List<HospitalStaff> getAllHospitalStaff();

    List<HospitalStaff> getAllHospitalStaffPageable(int offset, int numberOfRows);

    List<HospitalStaff> getAllHospitalStaffPageableAndSortedAlphabetically(int offset, int numberOfRows);

    List<HospitalStaff> getAllHospitalStaffPageableAndSortedByCategory(int offset, int numberOfRows);

    List<HospitalStaff> getAllHospitalStaffPageableAndSortedByNumberOfPatients(int offset, int numberOfRows);

    int getNumberOfRows();

    List<HospitalStaff> getAllHospitalStaffByPatientId(long id);


    void deleteStaffById(long id);

    void updateStaffUsernameById(long id, String username);

    void updateStaffPasswordById(long id, String password);

    void updateStaffRoleById(long id, String role);

    void updateStaffCategoryById(long id, String category);

    void updateStaffFirstNameById(long id, String firstName);

    void updateStaffLastNameById(long id, String lastName);

    void updateStaffNumberOfPatientsById(long id, long numberOfPatients);
}
