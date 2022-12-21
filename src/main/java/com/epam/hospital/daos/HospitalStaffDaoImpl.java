package com.epam.hospital.daos;

import com.epam.hospital.DataSource;
import com.epam.hospital.models.HospitalStaff;
import com.epam.hospital.repositories.Sorting;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HospitalStaffDaoImpl implements HospitalStaffDao {

    private static final String FIND_BY_ID = "select * from hospital_staff where id=?";
    private static final String FIND_ALL = "select * from hospital_staff";
    private static final String SAVE = "insert into hospital_staff values (default,?,?,?,?,?,?,?)";
    private static final String DELETE = "delete from hospital_staff where id=?";
    private static final String UPDATE = "update hospital_staff set username=?, password=?, role_id=?, category_id=?, " +
            "first_name=?, last_name=?, number_of_patients=?  where id=?";
    private static final String FIND_BY_USERNAME = "select * from hospital_staff where username=?";
    private static final String FIND_ALL_PAGEABLE = "select * from hospital_staff limit ? offset ?";

    private static final String FIND_ALL_BY_PATIENT_ID = "select * from hospital_staff_patients hp full join hospital_staff hs " +
            "on hs.id = hp.hospital_staff_id where patient_id=?";
    private static final String NUMBER_OF_ROWS = "select count(*) from hospital_staff";

    @Override
    public Optional<HospitalStaff> findById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong(++columnIndex));
                doctor.setUsername(resultSet.getString(++columnIndex));
                doctor.setPassword(resultSet.getString(++columnIndex));
                doctor.setRoleId(resultSet.getLong(++columnIndex));
                doctor.setCategoryId(resultSet.getLong(++columnIndex));
                doctor.setFirstName(resultSet.getString(++columnIndex));
                doctor.setLastName(resultSet.getString(++columnIndex));
                doctor.setNumberOfPatients(resultSet.getLong(++columnIndex));
                return Optional.of(doctor);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HospitalStaff> findAll() {
        List<HospitalStaff> doctors = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int columnIndex = 0;
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong(++columnIndex));
                doctor.setUsername(resultSet.getString(++columnIndex));
                doctor.setPassword(resultSet.getString(++columnIndex));
                doctor.setRoleId(resultSet.getLong(++columnIndex));
                doctor.setCategoryId(resultSet.getLong(++columnIndex));
                doctor.setFirstName(resultSet.getString(++columnIndex));
                doctor.setLastName(resultSet.getString(++columnIndex));
                doctor.setNumberOfPatients(resultSet.getLong(++columnIndex));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return doctors;
    }

    @Override
    public void save(HospitalStaff hospitalStaff) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, hospitalStaff.getUsername());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getPassword());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getRole().getId());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getCategory().getId());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getFirstName());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getLastName());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getNumberOfPatients());
            boolean isExecuted = preparedStatement.execute();
            if (isExecuted) log.info("New hospital staff is added: {}", hospitalStaff.getUsername());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            boolean isExecuted = preparedStatement.execute();
            if (isExecuted) log.info("New hospital staff with ID {} is deleted", id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(HospitalStaff hospitalStaff) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, hospitalStaff.getUsername());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getPassword());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getRoleId());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getCategoryId());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getFirstName());
            preparedStatement.setString(++parameterIndex, hospitalStaff.getLastName());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getNumberOfPatients());
            preparedStatement.setLong(++parameterIndex, hospitalStaff.getId());
            boolean isExecuted = preparedStatement.execute();
            if (isExecuted) log.info("Hospital staff is updated: {}", hospitalStaff.getUsername());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<HospitalStaff> findByUsername(String username) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong(++columnIndex));
                doctor.setUsername(resultSet.getString(++columnIndex));
                doctor.setPassword(resultSet.getString(++columnIndex));
                doctor.setRoleId(resultSet.getLong(++columnIndex));
                doctor.setCategoryId(resultSet.getLong(++columnIndex));
                doctor.setFirstName(resultSet.getString(++columnIndex));
                doctor.setLastName(resultSet.getString(++columnIndex));
                doctor.setNumberOfPatients(resultSet.getLong(++columnIndex));
                return Optional.of(doctor);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HospitalStaff> findAllPageable(int offset, int numberOfRows) {
        List<HospitalStaff> doctors = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PAGEABLE)) {
            preparedStatement.setInt(1, numberOfRows);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong(++columnIndex));
                doctor.setUsername(resultSet.getString(++columnIndex));
                doctor.setPassword(resultSet.getString(++columnIndex));
                doctor.setRoleId(resultSet.getLong(++columnIndex));
                doctor.setCategoryId(resultSet.getLong(++columnIndex));
                doctor.setFirstName(resultSet.getString(++columnIndex));
                doctor.setLastName(resultSet.getString(++columnIndex));
                doctor.setNumberOfPatients(resultSet.getLong(++columnIndex));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return doctors;
    }

    @Override
    public List<HospitalStaff> findAllPageableAndSorted(int offset, int numberOfRows, Sorting sortBy) {
        String query = "select * from hospital_staff order by " + sortBy.getValue() + " limit ? offset ?";
        List<HospitalStaff> doctors = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, numberOfRows);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong(++columnIndex));
                doctor.setUsername(resultSet.getString(++columnIndex));
                doctor.setPassword(resultSet.getString(++columnIndex));
                doctor.setRoleId(resultSet.getLong(++columnIndex));
                doctor.setCategoryId(resultSet.getLong(++columnIndex));
                doctor.setFirstName(resultSet.getString(++columnIndex));
                doctor.setLastName(resultSet.getString(++columnIndex));
                doctor.setNumberOfPatients(resultSet.getLong(++columnIndex));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return doctors;
    }

    @Override
    public List<HospitalStaff> findAllByPatientId(long id) {
        List<HospitalStaff> doctors = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_PATIENT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                HospitalStaff doctor = new HospitalStaff();
                doctor.setId(resultSet.getLong("hospital_staff_id"));
                doctor.setUsername(resultSet.getString("username"));
                doctor.setPassword(resultSet.getString("password"));
                doctor.setRoleId(resultSet.getLong("role_id"));
                doctor.setCategoryId(resultSet.getLong("category_id"));
                doctor.setFirstName(resultSet.getString("first_name"));
                doctor.setLastName(resultSet.getString("last_name"));
                doctor.setNumberOfPatients(resultSet.getLong("number_of_patients"));
                doctors.add(doctor);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return doctors;
    }

    @Override
    public int getNumberOfRows() {
        int numberOfRows = 0;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(NUMBER_OF_ROWS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return numberOfRows;
    }

}
