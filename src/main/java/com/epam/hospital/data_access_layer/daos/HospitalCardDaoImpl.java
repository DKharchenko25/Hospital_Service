package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.DataSource;
import com.epam.hospital.data_access_layer.models.HospitalCard;
import com.epam.hospital.data_access_layer.models.HospitalStaff;
import com.epam.hospital.data_access_layer.models.Patient;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class HospitalCardDaoImpl implements Dao<HospitalCard> {

    private static final String FIND_BY_ID = "select * from hospital_card where id=?";
    private static final String FIND_ALL = "select * from hospital_card";
    private static final String SAVE = "insert into hospital_card values (default,?,?,?,?,?,?,?)";
    private static final String DELETE = "delete from hospital_card where id=?";
    private static final String UPDATE = "update hospital_card set patient_id=?, doctor_id=?, record_date=?, " +
            "procedures=?, medications=?, operations=?, diagnosis=? where id=?";

    @Override
    public Optional<HospitalCard> findById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                HospitalCard hospitalCard = new HospitalCard();
                hospitalCard.setId(resultSet.getLong(++columnIndex));
                hospitalCard.setPatient(new Patient(resultSet.getLong(++columnIndex)));
                hospitalCard.setDoctor(new HospitalStaff(resultSet.getLong(++columnIndex)));
                hospitalCard.setRecordDate(resultSet.getDate(++columnIndex));
                hospitalCard.setProcedures(resultSet.getString(++columnIndex));
                hospitalCard.setMedications(resultSet.getString(++columnIndex));
                hospitalCard.setOperations(resultSet.getString(++columnIndex));
                hospitalCard.setDiagnosis(resultSet.getString(++columnIndex));
                return Optional.of(hospitalCard);
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<HospitalCard> findAll() {
        List<HospitalCard> hospitalCards = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int columnIndex = 0;
                HospitalCard hospitalCard = new HospitalCard();
                hospitalCard.setId(resultSet.getLong(++columnIndex));
                hospitalCard.setPatient(new Patient(resultSet.getLong(++columnIndex)));
                hospitalCard.setDoctor(new HospitalStaff(resultSet.getLong(++columnIndex)));
                hospitalCard.setRecordDate(resultSet.getDate(++columnIndex));
                hospitalCard.setProcedures(resultSet.getString(++columnIndex));
                hospitalCard.setMedications(resultSet.getString(++columnIndex));
                hospitalCard.setOperations(resultSet.getString(++columnIndex));
                hospitalCard.setDiagnosis(resultSet.getString(++columnIndex));
                hospitalCards.add(hospitalCard);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return hospitalCards;
    }

    @Override
    public void save(HospitalCard hospitalCard) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            int parameterIndex = 0;
            preparedStatement.setLong(++parameterIndex, hospitalCard.getPatient().getId());
            preparedStatement.setLong(++parameterIndex, hospitalCard.getDoctor().getId());
            preparedStatement.setDate(++parameterIndex, hospitalCard.getRecordDate());
            preparedStatement.setString(++parameterIndex, hospitalCard.getProcedures());
            preparedStatement.setString(++parameterIndex, hospitalCard.getMedications());
            preparedStatement.setString(++parameterIndex, hospitalCard.getOperations());
            preparedStatement.setString(++parameterIndex, hospitalCard.getDiagnosis());
            preparedStatement.execute();
            log.info("New card with is added for patient: {}", hospitalCard.getPatient().getUsername());
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
            log.info("Card with ID {} is deleted", id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(HospitalCard hospitalCard) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int parameterIndex = 0;
            preparedStatement.setLong(++parameterIndex, hospitalCard.getPatient().getId());
            preparedStatement.setLong(++parameterIndex, hospitalCard.getDoctor().getId());
            preparedStatement.setDate(++parameterIndex, hospitalCard.getRecordDate());
            preparedStatement.setString(++parameterIndex, hospitalCard.getProcedures());
            preparedStatement.setString(++parameterIndex, hospitalCard.getMedications());
            preparedStatement.setString(++parameterIndex, hospitalCard.getOperations());
            preparedStatement.setString(++parameterIndex, hospitalCard.getDiagnosis());
            preparedStatement.setLong(++parameterIndex, hospitalCard.getId());
            preparedStatement.execute();
            log.info("Card with ID {} is updated", hospitalCard.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
