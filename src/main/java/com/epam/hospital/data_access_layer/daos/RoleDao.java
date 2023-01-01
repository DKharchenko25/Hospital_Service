package com.epam.hospital.data_access_layer.daos;

import com.epam.hospital.data_access_layer.models.Role;

import java.util.Optional;

public interface RoleDao extends Dao<Role> {

    Optional<Role> findByName(String name);
}
