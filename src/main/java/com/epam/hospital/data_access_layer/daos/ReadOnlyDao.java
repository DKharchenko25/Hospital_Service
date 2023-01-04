package com.epam.hospital.data_access_layer.daos;

import java.util.List;
import java.util.Optional;

public interface ReadOnlyDao<T> {

    Optional<T> findById(long id);

    Optional<T> findByName(String name);

    List<T> findAll();
}
