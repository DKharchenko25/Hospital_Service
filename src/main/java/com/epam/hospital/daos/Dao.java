package com.epam.hospital.daos;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> findById(long id);

    List<T> findAll();

    void save(T t);

    void deleteById(long id);

    void update(T t);
}
