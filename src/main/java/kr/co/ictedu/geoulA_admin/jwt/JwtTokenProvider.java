package kr.co.ictedu.geoulA_admin.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

	// JWT Token을 생성 - 인증된 사용자에게 발급을 함 -> 클라이언트 (React)
	public String createToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		Date now = new Date();

		// JWT가 발급되고 나서 약 1시간(3600000 밀리초) 동안 만료시간을 설정
		Date expireDate = new Date(now.getTime() + 3600000);
		
		String role = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
    			.findFirst().orElse("ADMIN");
		System.out.println("UserDetails =>"+userDetails.getUsername() +","+role);

		String jwtResult = Jwts.builder().setSubject(userDetails.getUsername())
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
		System.out.println("JwtTokenProvider.jwtResult: " + jwtResult);
		return jwtResult;
	}
	
	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// 반드시 Bearer 시작해야 함
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		// 토큰 유효성/만료 여부 검증
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			// 토큰값이 유효하면 true
			return true;
		} catch (MalformedJwtException ex) { // 토큰의 형식이 올바르지 않을 때 발생
			log.error("토큰의 형식이 올바르지 않음");
		} catch (ExpiredJwtException ex) {
			log.error("토큰이 만료되었을 때 발생");
		} catch (UnsupportedJwtException ex) {
			log.error("지원되지 않는 토큰 유형일 때 발생");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty : 토큰이 비어있거나 null일 때 발생");
		} catch (SignatureException e) {
			log.error("토큰의 서명이 유효하지 않을 때 발생");
		}
		// 토큰이 유효하지 않으면 false
		return false;
	}
	
	public String getUsername(String token) {
		// 토큰 내용 추출
		return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token).getBody().getSubject();
		// 결과: JWT의 Subject(토큰의 소유자 정보)를 추출하여 반환한다.
	}

	public String getRole(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("role",String.class);
	}
}
