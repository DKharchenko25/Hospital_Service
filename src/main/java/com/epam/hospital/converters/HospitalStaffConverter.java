package com.epam.hospital.converters;

import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.models.HospitalStaff;

public class HospitalStaffConverter {

    public static HospitalStaffDto convertHospitalStaffToDto(HospitalStaff hospitalStaff) {
        HospitalStaffDto dto = new HospitalStaffDto();
        dto.setId(hospitalStaff.getId());
        dto.setUsername(hospitalStaff.getUsername());
        dto.setPassword(hospitalStaff.getPassword());
        dto.setRole(hospitalStaff.getRole());
        dto.setCategory(hospitalStaff.getCategory());
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
        hospitalStaff.setRoleName(dto.getRoleName());
        hospitalStaff.setCategoryName(dto.getCategoryName());
        hospitalStaff.setFirstName(dto.getFirstName());
        hospitalStaff.setLastName(dto.getLastName());
        hospitalStaff.setNumberOfPatients(dto.getNumberOfPatients());
        return hospitalStaff;
    }
}
