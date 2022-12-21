package com.epam.hospital.facades;

import com.epam.hospital.InvalidInputException;
import com.epam.hospital.converters.HospitalStaffConverter;
import com.epam.hospital.dtos.HospitalStaffDto;
import com.epam.hospital.services.HospitalStaffService;
import com.epam.hospital.validators.StaffInputValidator;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.epam.hospital.converters.HospitalStaffConverter.convertDtoToHospitalStaff;
import static com.epam.hospital.converters.HospitalStaffConverter.convertHospitalStaffToDto;
import static com.epam.hospital.validators.InputValidator.*;

public class HospitalStaffFacadeImpl implements HospitalStaffFacade {

    private final HospitalStaffService hospitalStaffService;

    public HospitalStaffFacadeImpl(HospitalStaffService hospitalStaffService) {
        this.hospitalStaffService = hospitalStaffService;
    }

    @Override
    public void addHospitalStaff(@NonNull HospitalStaffDto dto) {
        try {
            StaffInputValidator.validateStaff(dto);
        } catch (InvalidInputException e) {
            throw new IllegalArgumentException(e);
        }
        hospitalStaffService.addHospitalStaff(convertDtoToHospitalStaff(dto));
    }

    @Override
    public HospitalStaffDto getHospitalStaffById(@NonNull Long id) {
        return convertHospitalStaffToDto(hospitalStaffService.getHospitalStaffById(id));
    }

    @Override
    public HospitalStaffDto getHospitalStaffByUsername(@NonNull String username) {
        return convertHospitalStaffToDto(hospitalStaffService.getHospitalStaffByUsername(username));
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaff() {
        return hospitalStaffService.getAllHospitalStaff().stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaffPageable(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffService.getAllHospitalStaffPageable(offset, numberOfRows).stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }

    @Override
    public int getNumberOfRows() {
        return hospitalStaffService.getNumberOfRows();
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaffSortedAlphabetically(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffService.getAllHospitalStaffSortedAlphabetically(offset, numberOfRows).stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaffSortedByCategory(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffService.getAllHospitalStaffSortedByCategory(offset, numberOfRows).stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaffSortedByNumberOfPatients(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffService.getAllHospitalStaffSortedByNumberOfPatients(offset, numberOfRows).stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }

    @Override
    public List<HospitalStaffDto> getAllHospitalStaffByPatientId(@NonNull Long id) {
        return hospitalStaffService.getAllHospitalStaffByPatientId(id).stream()
                .map(HospitalStaffConverter::convertHospitalStaffToDto).collect(Collectors.toList());
    }


    @Override
    public void deleteHospitalStaffById(@NonNull Long id) {
        hospitalStaffService.deleteHospitalStaffById(id);
    }

    @Override
    public void updateStaffUsernameById(@NonNull Long id, @NonNull String username) {
        if (validateUsername(username)) {
            hospitalStaffService.updateStaffUsernameById(id, username);
        } else {
            throw new IllegalArgumentException("Invalid username: " + username);
        }
    }

    @Override
    public void updateStaffPasswordById(@NonNull Long id, @NonNull String password) {
        if (validatePassword(password)) {
            hospitalStaffService.updateStaffPasswordById(id, password);
        } else {
            throw new IllegalArgumentException("Invalid password: " + password);
        }
    }

    @Override
    public void updateStaffRoleById(@NonNull Long id, @NonNull String role) {
        hospitalStaffService.updateStaffRoleById(id, role);
    }

    @Override
    public void updateStaffCategoryById(@NonNull Long id, @NonNull String category) {
        hospitalStaffService.updateStaffCategoryById(id, category);
    }

    @Override
    public void updateStaffFirstNameById(@NonNull Long id, @NonNull String firstName) {
        if (validateName(firstName)) {
            hospitalStaffService.updateStaffFirstNameById(id, firstName);
        } else {
            throw new IllegalArgumentException("Invalid first name: " + firstName);
        }
    }

    @Override
    public void updateStaffLastNameById(@NonNull Long id, @NonNull String lastName) {
        if (validateName(lastName)) {
            hospitalStaffService.updateStaffLastNameById(id, lastName);
        } else {
            throw new IllegalArgumentException("Invalid last name: " + lastName);
        }
    }
}
