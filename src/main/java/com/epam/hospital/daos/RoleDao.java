package com.epam.hospital.daos;

import com.epam.hospital.models.Role;

import java.util.Optional;

public interface RoleDao extends Dao<Role> {

    Optional<Role> findByName(String name);
}
