package com.epam.hospital.facades.converters;

import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.facades.dtos.HospitalStaffDto;

public class HospitalStaffConverter {

    public static HospitalStaffDto convertHospitalStaffToDto(HospitalStaff hospitalStaff) {
        HospitalStaffDto dto = new HospitalStaffDto();
        dto.setId(hospitalStaff.getId());
        dto.setUsername(hospitalStaff.getUsername());
        dto.setPassword(hospitalStaff.getPassword());
        dto.setRole(RoleConverter.convertRoleToRoleDto(hospitalStaff.getRole()));
        dto.setCategory(CategoryConverter.convertCategoryToCategoryDto(hospitalStaff.getCategory()));
        dto.setFirstName(hospitalStaff.getFirstName());
        dto.setLastName(hospitalStaff.getLastName());
        dto.setNumberOfPatients(hospitalStaff.getNumberOfPatients());
        return dto;

    }

    public static HospitalStaff convertDtoToHospitalStaff(HospitalStaffDto dto) {
        HospitalStaff hospitalStaff = new HospitalStaff();
        hospitalStaff.setId(dto.getId());
        hospitalStaff.setUsername(dto.getUsername());
        hospitalStaff.setPassword(dto.getPassword());
        hospitalStaff.setRole(RoleConverter.convertRoleDtoToRole(dto.getRole()));
        hospitalStaff.setCategory(CategoryConverter.convertCategoryDtoToCategory(dto.getCategory()));
        hospitalStaff.setFirstName(dto.getFirstName());
        hospitalStaff.setLastName(dto.getLastName());
        hospitalStaff.setNumberOfPatients(dto.getNumberOfPatients());
        return hospitalStaff;
    }
}
