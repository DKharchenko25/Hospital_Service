package com.epam.hospital.facades.converters;

import com.epam.hospital.data_access_layer.models.Role;
import com.epam.hospital.facades.dtos.RoleDto;

public class RoleConverter {

    public static RoleDto convertRoleToRoleDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }

    public static Role convertRoleDtoToRole(RoleDto dto) {
        return new Role(dto.getId(), dto.getName());
    }
}
