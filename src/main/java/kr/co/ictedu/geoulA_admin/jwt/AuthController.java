package kr.co.ictedu.geoulA_admin.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAdmin(@RequestBody AuthRequest authRequest) {
		System.out.println("USERNAME: " + authRequest.getUsername());
		System.out.println("PASSWORD: " + authRequest.getPassword());
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
						authRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.createToken(authentication);
		System.out.println("JWT LOG: " + jwt);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logoutAdmin() {
		SecurityContextHolder.clearContext();
		return ResponseEntity.ok("Logout Successful");
	}

}
