package com.epam.hospital.validators;

import com.epam.hospital.InvalidInputException;
import com.epam.hospital.daos.HospitalStaffDao;
import com.epam.hospital.daos.HospitalStaffDaoImpl;
import com.epam.hospital.dtos.HospitalStaffDto;

public class StaffInputValidator extends InputValidator {

    public static boolean isUsernameExists(String username) {
        HospitalStaffDao hospitalStaffDao = new HospitalStaffDaoImpl();
        return hospitalStaffDao.findByUsername(username).isPresent();
    }

    public static void validateStaff(HospitalStaffDto dto) throws InvalidInputException {
        if (!validateUsername(dto.getUsername()))
            throw new InvalidInputException("Invalid username: " + dto.getUsername());
        if (!validatePassword(dto.getPassword()))
            throw new InvalidInputException("Invalid password: " + dto.getPassword());
        if (!validateName(dto.getFirstName()))
            throw new InvalidInputException("Invalid first name: " + dto.getFirstName());
        if (!validateName(dto.getLastName()))
            throw new InvalidInputException("Invalid last name: " + dto.getLastName());
    }
}
