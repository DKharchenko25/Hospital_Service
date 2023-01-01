package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.models.Category;

import java.util.Optional;

public interface CategoryDao extends Dao<Category> {

    Optional<Category> findByName(String name);
}
