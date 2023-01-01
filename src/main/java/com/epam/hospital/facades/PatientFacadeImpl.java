package com.epam.hospital.facades;

import com.epam.hospital.exceptions.InvalidInputException;
import com.epam.hospital.facades.converters.PatientConverter;
import com.epam.hospital.facades.dtos.PatientDto;
import com.epam.hospital.facades.validators.PatientInputValidator;
import com.epam.hospital.services.PatientService;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.facades.converters.PatientConverter.convertDtoToPatient;
import static com.epam.hospital.facades.converters.PatientConverter.convertPatientToDto;
import static com.epam.hospital.facades.validators.InputValidator.*;

public class PatientFacadeImpl implements PatientFacade {

    private final PatientService patientService;


    public PatientFacadeImpl(PatientService patientService) {
        this.patientService = patientService;

    }

    @Override
    public void addPatient(@NonNull PatientDto dto) {
        try {
            PatientInputValidator.validatePatient(dto);
        } catch (InvalidInputException e) {
            throw new IllegalArgumentException(e);
        }
        patientService.addPatient(convertDtoToPatient(dto));
    }

    @Override
    public PatientDto getPatientById(@NonNull Long id) {
        return convertPatientToDto(patientService.getPatientById(id));
    }

    @Override
    public PatientDto getPatientByUsername(@NonNull String username) {
        return convertPatientToDto(patientService.getPatientByUsername(username));
    }

    @Override
    public List<PatientDto> getAllPatientsByHospitalStaffId(@NonNull Long id) {
        return patientService.getAllPatientsByHospitalStaffId(id).stream()
                .map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }


    @Override
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients().stream()
                .map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getAllPatientsPageable(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientService.getAllPatientsPageable(offset, numberOfRows).stream()
                .map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }

    @Override
    public int getNumberOfRows() {
        return patientService.getNumberOfRows();
    }

    @Override
    public List<PatientDto> getAllPatientsSortedAlphabetically(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientService.getAllPatientsSortedAlphabetically(offset, numberOfRows).stream()
                .map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> getAllPatientsSortedByBirthDate(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return patientService.getAllPatientsSortedByBirthDate(offset, numberOfRows).stream()
                .map(PatientConverter::convertPatientToDto).collect(Collectors.toList());
    }

    @Override
    public void appointPatientToDoctor(@NonNull Long patientId, @NonNull Long doctorId) {
        patientService.appointPatientToDoctor(patientId, doctorId);
    }

    @Override
    public void dischargePatientById(@NonNull Long id) {
        patientService.dischargePatientById(id);
    }

    @Override
    public void deletePatientById(@NonNull Long id) {
        patientService.deletePatientById(id);
    }

    @Override
    public void updatePatientUsernameById(@NonNull Long id, @NonNull String username) {
        if (validateUsername(username)) {
            patientService.updatePatientUsernameById(id, username);
        } else {
            throw new IllegalArgumentException("Invalid username: " + username);
        }
    }


    @Override
    public void updatePatientPasswordById(@NonNull Long id, @NonNull String password) {
        if (validatePassword(password)) {
            patientService.updatePatientPasswordById(id, password);
        } else {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }

    @Override
    public void updatePatientFirstNameById(@NonNull Long id, @NonNull String firstName) {
        if (validateName(firstName)) {
            patientService.updatePatientFirstNameById(id, firstName);
        } else {
            throw new IllegalArgumentException("Invalid first name: " + firstName);
        }
    }

    @Override
    public void updatePatientLastNameById(@NonNull Long id, @NonNull String lastName) {
        if (validateName(lastName)) {
            patientService.updatePatientLastNameById(id, lastName);
        } else {
            throw new IllegalArgumentException("Invalid last name: " + lastName);
        }
    }

    @Override
    public void updatePatientBirthDateById(@NonNull Long id, @NonNull String date) {
        if (validateDate(date)) {
            patientService.updatePatientBirthDateById(id, date);
        } else {
            throw new IllegalArgumentException("Invalid birth date: " + date);
        }

    }

    @Override
    public void updatePatientEmailById(@NonNull Long id, @NonNull String email) {
        if (validateEmail(email)) {
            patientService.updatePatientEmailById(id, email);
        } else {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    @Override
    public void updatePatientPhoneNumberById(@NonNull Long id, @NonNull String phoneNumber) {
        if (validatePhoneNumber(phoneNumber)) {
            patientService.updatePatientPhoneNumberById(id, phoneNumber);
        } else {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
    }

}
