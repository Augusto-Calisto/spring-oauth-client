package br.com.tech.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.tech.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	Optional<User> findBySubAndRegistrationId(String sub, String registrationId);

	@Modifying
	@Query("UPDATE User u SET u.lastLoginDateTime = :loginDateTime WHERE u.id = :id")
	void updateLastLoginDateTime(@Param("id") String id, @Param("loginDateTime") LocalDateTime loginDateTime);
}