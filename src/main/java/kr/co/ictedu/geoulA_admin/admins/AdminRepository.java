package kr.co.ictedu.geoulA_admin.admins;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUsername(String username);
	boolean existsByUsername(String username);
}
