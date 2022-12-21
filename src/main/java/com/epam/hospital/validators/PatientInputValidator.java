package com.epam.hospital.validators;

import com.epam.hospital.InvalidInputException;
import com.epam.hospital.daos.PatientDao;
import com.epam.hospital.daos.PatientDaoImpl;
import com.epam.hospital.dtos.PatientDto;

public class PatientInputValidator extends InputValidator {

    public static boolean isUsernameExists(String username) {
        PatientDao patientDao = new PatientDaoImpl();
        return patientDao.findByUsername(username).isPresent();
    }

    public static void validatePatient(PatientDto dto) throws InvalidInputException {
        if (!validateUsername(dto.getUsername()))
            throw new InvalidInputException("Invalid username: " + dto.getUsername());
        if (!validatePassword(dto.getPassword()))
            throw new InvalidInputException("Invalid password: " + dto.getPassword());
        if (!validateName(dto.getFirstName()))
            throw new InvalidInputException("Invalid first name: " + dto.getFirstName());
        if (!validateName(dto.getLastName()))
            throw new InvalidInputException("Invalid last name: " + dto.getLastName());
        if (!validateDate(dto.getBirthDate())) throw new InvalidInputException("Invalid date: " + dto.getBirthDate());
        if (!validateEmail(dto.getEmail())) throw new InvalidInputException("Invalid email: " + dto.getEmail());
        if (!validatePhoneNumber(dto.getPhoneNumber()))
            throw new InvalidInputException("Invalid phone number: " + dto.getPhoneNumber());
    }
}
