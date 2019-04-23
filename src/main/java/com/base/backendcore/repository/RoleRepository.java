package com.base.backendcore.repository;

import com.base.backendcore.model.Role;
import com.base.backendcore.model.enums.RoleNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(RoleNameEnum roleName);

    @Query("SELECT R FROM  Role R WHERE R.name in (:roles)")
    Set<Role> finfByNames(@Param("roles") Set<RoleNameEnum> rolesNames);
}
