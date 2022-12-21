package com.epam.hospital.daos;

import com.epam.hospital.models.Category;

import java.util.Optional;

public interface CategoryDao extends Dao<Category> {

    Optional<Category> findByName(String name);
}
