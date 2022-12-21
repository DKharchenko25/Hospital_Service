package com.epam.hospital.services;

import com.epam.hospital.models.HospitalStaff;
import com.epam.hospital.repositories.HospitalStaffRepository;
import com.epam.hospital.validators.PasswordEncoder;
import lombok.NonNull;

import java.util.List;

public class HospitalStaffServiceImpl implements HospitalStaffService {

    private final HospitalStaffRepository hospitalStaffRepository;

    public HospitalStaffServiceImpl(HospitalStaffRepository hospitalStaffRepository) {
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    @Override
    public void addHospitalStaff(@NonNull HospitalStaff hospitalStaff) {
        if (!hospitalStaffRepository.getHospitalStaffByUsername(hospitalStaff.getUsername()).isPresent()) {
            hospitalStaff.setPassword(PasswordEncoder.getEncodedPassword(hospitalStaff.getPassword()));
            hospitalStaffRepository.addStaff(hospitalStaff);
        } else {
            throw new IllegalArgumentException("User with username: " + hospitalStaff.getUsername() + " is already exists");
        }
    }

    private String getExceptionMessage(long id) {
        return String.format("User with ID# %d is not found", id);
    }

    @Override
    public HospitalStaff getHospitalStaffById(@NonNull Long id) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            return hospitalStaffRepository.getHospitalStaffById(id).get();
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public HospitalStaff getHospitalStaffByUsername(@NonNull String username) {
        if (hospitalStaffRepository.getHospitalStaffByUsername(username).isPresent()) {
            return hospitalStaffRepository.getHospitalStaffByUsername(username).get();
        } else {
            throw new IllegalArgumentException("Hospital staff with username: " + username + " is not found");
        }
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaff() {
        return hospitalStaffRepository.getAllHospitalStaff();
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffPageable(@NonNull Integer offset,
                                                           @NonNull Integer numberOfRows) {
        return hospitalStaffRepository.getAllHospitalStaffPageable(offset, numberOfRows);
    }

    @Override
    public int getNumberOfRows() {
        return hospitalStaffRepository.getNumberOfRows();
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffSortedAlphabetically(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffRepository.getAllHospitalStaffPageableAndSortedAlphabetically(offset, numberOfRows);
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffSortedByCategory(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByCategory(offset, numberOfRows);
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffSortedByNumberOfPatients(@NonNull Integer offset, @NonNull Integer numberOfRows) {
        return hospitalStaffRepository.getAllHospitalStaffPageableAndSortedByNumberOfPatients(offset, numberOfRows);
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffByPatientId(@NonNull Long id) {
        return hospitalStaffRepository.getAllHospitalStaffByPatientId(id);
    }


    @Override
    public void deleteHospitalStaffById(@NonNull Long id) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.deleteStaffById(id);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateStaffUsernameById(@NonNull Long id, @NonNull String username) {
        if (!hospitalStaffRepository.getHospitalStaffByUsername(username).isPresent() &&
                hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffUsernameById(id, username);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id) + " Or input username is already exists");
        }
    }

    @Override
    public void updateStaffPasswordById(@NonNull Long id, @NonNull String password) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffPasswordById(id, PasswordEncoder.getEncodedPassword(password));
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateStaffRoleById(@NonNull Long id, @NonNull String role) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffRoleById(id, role);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateStaffCategoryById(@NonNull Long id, @NonNull String category) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffCategoryById(id, category);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateStaffFirstNameById(@NonNull Long id, @NonNull String firstName) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffFirstNameById(id, firstName);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateStaffLastNameById(@NonNull Long id, @NonNull String lastName) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffLastNameById(id, lastName);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }

    @Override
    public void updateNumberOfPatientsById(@NonNull Long id, @NonNull Long numberOfPatients) {
        if (hospitalStaffRepository.getHospitalStaffById(id).isPresent()) {
            hospitalStaffRepository.updateStaffNumberOfPatientsById(id, numberOfPatients);
        } else {
            throw new IllegalArgumentException(getExceptionMessage(id));
        }
    }
}
