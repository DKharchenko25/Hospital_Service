package com.epam.hospital.facades.validators;

import com.epam.hospital.data_access_layer.daos.HospitalStaffDao;
import com.epam.hospital.data_access_layer.daos.HospitalStaffDaoImpl;
import com.epam.hospital.exceptions.InvalidInputException;
import com.epam.hospital.facades.dtos.HospitalStaffDto;

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
