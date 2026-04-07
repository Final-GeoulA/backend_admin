package kr.co.ictedu.geoulA_admin.security;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.ictedu.geoulA_admin.admins.Admin;
import kr.co.ictedu.geoulA_admin.admins.AdminRepository;
import kr.co.ictedu.geoulA_admin.admins.Role;

@SpringBootTest
public class SecurityAdminRepositoryTest {

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Test
	void insertAdmin() {
		String encodedPass = passwordEncoder.encode("admin");
		System.out.println("Encoded Password: " + encodedPass);
		
		List<Admin> admin = Arrays.asList(
				new Admin("super",encodedPass,Role.SUPERADMIN),
				new Admin("admin",encodedPass,Role.ADMIN)
				);
		for (Admin individual : admin) {
			if (!adminRepository.existsByUsername(individual.getUsername())) {
				adminRepository.save(individual);
			}
		}
	}
}
