package kr.co.ictedu.geoulA_admin.admins;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomAdminDetailsService implements UserDetailsService {

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Optional<Admin> => " + adminRepository.findByUsername(username));
		try {
			return adminRepository.findByUsername(username).orElseThrow(() -> new Exception("Admin Not Found"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
