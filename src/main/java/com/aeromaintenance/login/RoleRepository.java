package com.aeromaintenance.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findById(Long roleId);
	Optional<Role> findByRoleName(String roleName);

}
