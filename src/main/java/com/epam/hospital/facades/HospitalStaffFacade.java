package com.epam.hospital.facades;

import com.epam.hospital.facades.dtos.HospitalStaffDto;

import java.util.List;

public interface HospitalStaffFacade {
    void addHospitalStaff(HospitalStaffDto dto);

    HospitalStaffDto getHospitalStaffById(Long id);

    HospitalStaffDto getHospitalStaffByUsername(String username);

    List<HospitalStaffDto> getAllHospitalStaff();

    List<HospitalStaffDto> getAllHospitalStaffPageable(Integer offset, Integer numberOfRows);

    int getNumberOfRows();

    List<HospitalStaffDto> getAllHospitalStaffSortedAlphabetically(Integer offset, Integer numberOfRows);

    List<HospitalStaffDto> getAllHospitalStaffSortedByCategory(Integer offset, Integer numberOfRows);

    List<HospitalStaffDto> getAllHospitalStaffSortedByNumberOfPatients(Integer offset, Integer numberOfRows);

    List<HospitalStaffDto> getAllHospitalStaffByPatientId(Long id);

    void deleteHospitalStaffById(Long id);

    void updateStaffUsernameById(Long id, String username);

    void updateStaffPasswordById(Long id, String password);

    void updateStaffRoleById(Long id, String role);

    void updateStaffCategoryById(Long id, String category);

    void updateStaffFirstNameById(Long id, String firstName);

    void updateStaffLastNameById(Long id, String lastName);
}
