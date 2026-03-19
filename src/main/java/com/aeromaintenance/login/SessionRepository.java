package com.aeromaintenance.login;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<UserSession, Long>{
	
	Optional<UserSession> findByUserIdAndIsActiveTrue(Long userId);
	Optional<UserSession> findByRefreshTokenAndIsActiveTrue(String refreshToken);
	Optional<UserSession> findByAccessTokenAndIsActiveTrue(String accessToken);

}
