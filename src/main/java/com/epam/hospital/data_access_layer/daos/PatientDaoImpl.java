package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.DataSource;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.utils.Sorting;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class PatientDaoImpl implements PatientDao {

    private static final String FIND_BY_ID = "select * from patients where id=?";
    private static final String FIND_ALL = "select * from patients";
    private static final String SAVE = "insert into patients values (default,?,?,?,?,?,?,?)";
    private static final String DELETE = "delete from patients where id=?";
    private static final String UPDATE = "update patients set username=?, password=?, first_name=?, last_name=?, birth_date=?," +
            "email=?, phone_number=? where id=?";
    private static final String FIND_BY_USERNAME = "select * from patients where username=?";
    private static final String FIND_ALL_PAGEABLE = "select * from patients limit ? offset ?";

    private static final String FIND_ALL_BY_HOSPITAL_STAFF_ID = "select * from hospital_staff_patients hp full join patients p " +
            "on p.id = hp.patient_id where hospital_staff_id=?";
    private static final String NUMBER_OF_ROWS = "select count(*) from patients";

    private static final String APPOINT = "insert into hospital_staff_patients values (?,?)";
    private static final String DISCHARGE = "delete from hospital_staff_patients where hospital_staff_id=? and patient_id=?";


    @Override
    public Optional<Patient> findById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Patient patient = new Patient();
                patient.setId(resultSet.getLong(++columnIndex));
                patient.setUsername(resultSet.getString(++columnIndex));
                patient.setPassword(resultSet.getString(++columnIndex));
                patient.setFirstName(resultSet.getString(++columnIndex));
                patient.setLastName(resultSet.getString(++columnIndex));
                patient.setBirthDate(resultSet.getString(++columnIndex));
                patient.setEmail(resultSet.getString(++columnIndex));
                patient.setPhoneNumber(resultSet.getString(++columnIndex));
                return Optional.of(patient);
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int columnIndex = 0;
                Patient patient = new Patient();
                patient.setId(resultSet.getLong(++columnIndex));
                patient.setUsername(resultSet.getString(++columnIndex));
                patient.setPassword(resultSet.getString(++columnIndex));
                patient.setFirstName(resultSet.getString(++columnIndex));
                patient.setLastName(resultSet.getString(++columnIndex));
                patient.setBirthDate(resultSet.getString(++columnIndex));
                patient.setEmail(resultSet.getString(++columnIndex));
                patient.setPhoneNumber(resultSet.getString(++columnIndex));
                patients.add(patient);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return patients;
    }

    @Override
    public void save(Patient patient) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, patient.getUsername());
            preparedStatement.setString(++parameterIndex, patient.getPassword());
            preparedStatement.setString(++parameterIndex, patient.getFirstName());
            preparedStatement.setString(++parameterIndex, patient.getLastName());
            preparedStatement.setDate(++parameterIndex, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(++parameterIndex, patient.getEmail());
            preparedStatement.setString(++parameterIndex, patient.getPhoneNumber());
            preparedStatement.execute();
            log.info("New patient is added: {}", patient.getUsername());
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
            preparedStatement.execute();
            log.info("Patient with ID {} is deleted", id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Patient patient) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, patient.getUsername());
            preparedStatement.setString(++parameterIndex, patient.getPassword());
            preparedStatement.setString(++parameterIndex, patient.getFirstName());
            preparedStatement.setString(++parameterIndex, patient.getLastName());
            preparedStatement.setDate(++parameterIndex, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(++parameterIndex, patient.getEmail());
            preparedStatement.setString(++parameterIndex, patient.getPhoneNumber());
            preparedStatement.setLong(++parameterIndex, patient.getId());
            preparedStatement.execute();
            log.info("Patient is updated: {}", patient.getUsername());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Patient> findByUsername(String username) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Patient patient = new Patient();
                patient.setId(resultSet.getLong(++columnIndex));
                patient.setUsername(resultSet.getString(++columnIndex));
                patient.setPassword(resultSet.getString(++columnIndex));
                patient.setFirstName(resultSet.getString(++columnIndex));
                patient.setLastName(resultSet.getString(++columnIndex));
                patient.setBirthDate(resultSet.getString(++columnIndex));
                patient.setEmail(resultSet.getString(++columnIndex));
                patient.setPhoneNumber(resultSet.getString(++columnIndex));
                return Optional.of(patient);
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> findAllPageable(int offset, int numberOfRows) {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_PAGEABLE)) {
            preparedStatement.setInt(1, numberOfRows);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                Patient patient = new Patient();
                patient.setId(resultSet.getLong(++columnIndex));
                patient.setUsername(resultSet.getString(++columnIndex));
                patient.setPassword(resultSet.getString(++columnIndex));
                patient.setFirstName(resultSet.getString(++columnIndex));
                patient.setLastName(resultSet.getString(++columnIndex));
                patient.setBirthDate(resultSet.getString(++columnIndex));
                patient.setEmail(resultSet.getString(++columnIndex));
                patient.setPhoneNumber(resultSet.getString(++columnIndex));
                patients.add(patient);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return patients;
    }

    @Override
    public List<Patient> findAllPageableAndSorted(int offset, int numberOfRows, Sorting sortBy) {
        String query = "select * from patients order by " + sortBy.getPersistenceValue() + " limit ? offset ?";
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, numberOfRows);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int columnIndex = 0;
                Patient patient = new Patient();
                patient.setId(resultSet.getLong(++columnIndex));
                patient.setUsername(resultSet.getString(++columnIndex));
                patient.setPassword(resultSet.getString(++columnIndex));
                patient.setFirstName(resultSet.getString(++columnIndex));
                patient.setLastName(resultSet.getString(++columnIndex));
                patient.setBirthDate(resultSet.getString(++columnIndex));
                patient.setEmail(resultSet.getString(++columnIndex));
                patient.setPhoneNumber(resultSet.getString(++columnIndex));
                patients.add(patient);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return patients;
    }

    @Override
    public List<Patient> findAllByHospitalStaffId(long id) {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_HOSPITAL_STAFF_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Patient patient = new Patient();
                patient.setId(resultSet.getLong("patient_id"));
                patient.setUsername(resultSet.getString("username"));
                patient.setPassword(resultSet.getString("password"));
                patient.setFirstName(resultSet.getString("first_name"));
                patient.setLastName(resultSet.getString("last_name"));
                patient.setBirthDate(resultSet.getString("birth_date"));
                patient.setEmail(resultSet.getString("email"));
                patient.setPhoneNumber(resultSet.getString("phone_number"));
                patients.add(patient);
            }
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return patients;
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
            resultSet.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return numberOfRows;
    }

    @Override
    public void appointPatientToDoctor(long patientId, long doctorId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(APPOINT)) {
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setLong(2, patientId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dischargePatientFromDoctor(long patientId, long doctorId) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DISCHARGE)) {
            preparedStatement.setLong(1, doctorId);
            preparedStatement.setLong(2, patientId);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
