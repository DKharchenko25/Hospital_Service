package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.DataSource;
import com.epam.hospital.data_access_layer.models.Category;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class CategoryDaoImpl implements ReadOnlyDao<Category> {

    private static final String FIND_BY_ID = "select * from categories where id=?";
    private static final String FIND_ALL = "select * from categories";
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
            resultSet.close();
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
            resultSet.close();
            return Optional.empty();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
