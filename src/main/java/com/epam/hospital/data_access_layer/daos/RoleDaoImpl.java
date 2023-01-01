package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.DataSource;
import com.epam.hospital.data_access_layer.models.Role;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class RoleDaoImpl implements RoleDao {
    private static final String FIND_BY_ID = "select * from roles where id=?";
    private static final String FIND_ALL = "select * from roles";
    private static final String SAVE = "insert into roles values (default,?)";
    private static final String DELETE = "delete from roles where id=?";
    private static final String UPDATE = "update roles set name=? where id=?";
    private static final String FIND_BY_NAME = "select * from roles where name=?";

    @Override
    public Optional<Role> findById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Role role = new Role();
                role.setId(resultSet.getLong(++columnIndex));
                role.setName(resultSet.getString(++columnIndex));
                return Optional.of(role);
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int columnIndex = 0;
                roles.add(new Role(resultSet.getLong(++columnIndex), resultSet.getString(++columnIndex)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return roles;
    }

    @Override
    public void save(Role role) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.execute();
            log.info("New role is added: {}", role.getName());
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
            log.info("Role with ID {} is deleted", id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Role role) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, role.getName());
            preparedStatement.setLong(++parameterIndex, role.getId());
            preparedStatement.execute();
            log.info("Role with ID {} is updated", role.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Role> findByName(String name) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Role role = new Role();
                role.setId(resultSet.getLong(++columnIndex));
                role.setName(resultSet.getString(++columnIndex));
                return Optional.of(role);
            }
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
