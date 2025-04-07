package com.aeromaintenance.login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuMappingRepository extends JpaRepository<RoleMenuMapping, Long> {
    List<RoleMenuMapping> findByRole(Role role);
}
