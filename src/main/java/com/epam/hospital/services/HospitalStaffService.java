package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalStaff;

import java.util.List;

public interface HospitalStaffService {

    void addHospitalStaff(HospitalStaff hospitalStaff);

    HospitalStaff getHospitalStaffById(Long id);

    HospitalStaff getHospitalStaffByUsername(String username);

    List<HospitalStaff> getAllHospitalStaff();

    List<HospitalStaff> getAllHospitalStaffPageable(Integer offset, Integer numberOfRows);

    int getNumberOfRows();

    List<HospitalStaff> getAllHospitalStaffSortedAlphabetically(Integer offset, Integer numberOfRows);

    List<HospitalStaff> getAllHospitalStaffSortedByCategory(Integer offset, Integer numberOfRows);

    List<HospitalStaff> getAllHospitalStaffSortedByNumberOfPatients(Integer offset, Integer numberOfRows);

    List<HospitalStaff> getAllHospitalStaffByPatientId(Long id);

    void deleteHospitalStaffById(Long id);

    void updateStaffUsernameById(Long id, String username);

    void updateStaffPasswordById(Long id, String password);

    void updateStaffRoleById(Long id, String role);

    void updateStaffCategoryById(Long id, String category);

    void updateStaffFirstNameById(Long id, String firstName);

    void updateStaffLastNameById(Long id, String lastName);

    void updateNumberOfPatientsById(Long id, Long numberOfPatients);

}
