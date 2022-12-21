package com.epam.hospital.daos;

import com.epam.hospital.DataSource;
import com.epam.hospital.models.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryDaoImpl implements CategoryDao {

    private static final String FIND_BY_ID = "select * from categories where id=?";
    private static final String FIND_ALL = "select * from categories";
    private static final String SAVE = "insert into categories values (default,?)";
    private static final String DELETE = "delete from categories where id=?";
    private static final String UPDATE = "update categories set name=? where id=?";
    private static final String FIND_BY_NAME = "select * from categories where name=?";

    @Override
    public Optional<Category> findById(long id) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Category category = new Category();
                category.setId(resultSet.getLong(++columnIndex));
                category.setName(resultSet.getString(++columnIndex));
                return Optional.of(category);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int columnIndex = 0;
                categories.add(new Category(resultSet.getLong(++columnIndex), resultSet.getString(++columnIndex)));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void save(Category category) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setString(1, category.getName());
            boolean isExecuted = preparedStatement.execute();
            if (isExecuted) log.info("New category is added: {}", category.getName());
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
            if (isExecuted) log.info("Category with ID {} is deleted", id);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            int parameterIndex = 0;
            preparedStatement.setString(++parameterIndex, category.getName());
            preparedStatement.setLong(++parameterIndex, category.getId());
            boolean isExecuted = preparedStatement.execute();
            if (isExecuted) log.info("Category with ID {} is updated", category.getId());
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Category> findByName(String name) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int columnIndex = 0;
                Category category = new Category();
                category.setId(resultSet.getLong(++columnIndex));
                category.setName(resultSet.getString(++columnIndex));
                return Optional.of(category);
            }
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
