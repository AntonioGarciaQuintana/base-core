package com.base.backendcore.repository;

import com.base.backendcore.model.Role;
import com.base.backendcore.model.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleNameEnum roleName);
}
