package com.epam.hospital.data_access_layer.repositories;

import com.epam.hospital.data_access_layer.daos.CategoryDao;
import com.epam.hospital.data_access_layer.daos.HospitalStaffDao;
import com.epam.hospital.data_access_layer.daos.RoleDao;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.utils.Sorting;

import java.util.List;
import java.util.Optional;

public class HospitalStaffRepositoryImpl implements HospitalStaffRepository {

    private final HospitalStaffDao hospitalStaffDao;
    private final RoleDao roleDao;
    private final CategoryDao categoryDao;

    public HospitalStaffRepositoryImpl(HospitalStaffDao hospitalStaffDao, RoleDao roleDao, CategoryDao categoryDao) {
        this.hospitalStaffDao = hospitalStaffDao;
        this.roleDao = roleDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public void addStaff(HospitalStaff hospitalStaff) {
        hospitalStaff.setRole(roleDao.findByName(hospitalStaff.getRole().getName()).orElseThrow(IllegalArgumentException::new));
        hospitalStaff.setCategory(categoryDao.findByName(hospitalStaff.getCategory().getName()).orElseThrow(IllegalArgumentException::new));
        hospitalStaffDao.save(hospitalStaff);
    }

    @Override
    public Optional<HospitalStaff> getHospitalStaffById(long id) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
            return Optional.of(hospitalStaff);
        }
        return Optional.empty();

    }

    @Override
    public Optional<HospitalStaff> getHospitalStaffByUsername(String username) {
        if (hospitalStaffDao.findByUsername(username).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findByUsername(username).get();
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
            return Optional.of(hospitalStaff);
        }
        return Optional.empty();
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaff() {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAll();
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffPageable(int offset, int numberOfRows) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAllPageable(offset, numberOfRows);
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffPageableAndSortedAlphabetically(int offset, int numberOfRows) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAllPageableAndSorted(offset, numberOfRows, Sorting.ALPHABETICALLY);
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffPageableAndSortedByCategory(int offset, int numberOfRows) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAllPageableAndSortedByCategory(offset, numberOfRows);
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffPageableAndSortedByNumberOfPatients(int offset, int numberOfRows) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAllPageableAndSorted(offset, numberOfRows, Sorting.NUMBER_OF_PATIENTS);
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public int getNumberOfRows() {
        return hospitalStaffDao.getNumberOfRows();
    }

    @Override
    public List<HospitalStaff> getAllHospitalStaffByPatientId(long id) {
        List<HospitalStaff> hospitalStaffList = hospitalStaffDao.findAllByPatientId(id);
        hospitalStaffList.forEach(hospitalStaff -> {
            hospitalStaff.setRole(roleDao.findById(hospitalStaff.getRole().getId()).orElseThrow(IllegalArgumentException::new));
            hospitalStaff.setCategory(categoryDao.findById(hospitalStaff.getCategory().getId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalStaffList;
    }

    @Override
    public void deleteStaffById(long id) {
        hospitalStaffDao.deleteById(id);
    }

    @Override
    public void updateStaffUsernameById(long id, String username) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setUsername(username);
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffPasswordById(long id, String password) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setPassword(password);
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffRoleById(long id, String role) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setRole(roleDao.findByName(role).orElseThrow(IllegalArgumentException::new));
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffCategoryById(long id, String category) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setCategory(categoryDao.findByName(category).orElseThrow(IllegalArgumentException::new));
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffFirstNameById(long id, String firstName) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setFirstName(firstName);
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffLastNameById(long id, String lastName) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setLastName(lastName);
            hospitalStaffDao.update(hospitalStaff);
        }
    }

    @Override
    public void updateStaffNumberOfPatientsById(long id, long numberOfPatients) {
        if (hospitalStaffDao.findById(id).isPresent()) {
            HospitalStaff hospitalStaff = hospitalStaffDao.findById(id).get();
            hospitalStaff.setNumberOfPatients(numberOfPatients);
            hospitalStaffDao.update(hospitalStaff);
        }
    }
}
