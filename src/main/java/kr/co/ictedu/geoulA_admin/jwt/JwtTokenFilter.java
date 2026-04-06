package kr.co.ictedu.geoulA_admin.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Get the JWT token from the Authorization header
		// 토큰 추출: jwtTokenProvider.resolveToken(request)를 호출하여 HTTP 요청의 헤더에서 JWT 토큰을 추출
		// 처음 로그인하면 여기는 null이다
		// 로그인 -> 인증처리 -> 발급한 토큰을 받았을 때 null이 아님
		String token = jwtTokenProvider.resolveToken(request);
		System.out.println("JwtTokenFilter token ===========> " + token);
		if (token != null && jwtTokenProvider.validateToken(token)) {

			String username = jwtTokenProvider.getUsername(token);
			// JwtTokenFilter: 추출된 username => xman2@gmail.com
			System.out.println("JwtTokenFilter: 추출된 username => " + username);

			// Get the user details from the token
			// 유효한 토큰이 있을 경우, 토큰에서 사용자 이름을 추출하여(jwtTokenProvider.getUsername(token)) 해당 사용자의
			// 상세 정보를 로드
			UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUsername(token));
			// Create an authentication object and set it in the security context holder
			// UsernamePasswordAuthenticationToken 객체를 생성하여 사용자의 인증 정보를 나타낸다.
			// 이 객체에는 사용자의 권한(userDetails.getAuthorities())이 포함
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());
			// 생성된 인증 객체를 SecurityContextHolder에 설정하여,
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			// 이후의 요청 처리 과정에서 현재 사용자가 인증되었음을 SecurityContextHolder를 통해서 넘긴다.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		// filterChain.doFilter(request, response)를 호출하여 요청 처리를 계속 진행한다.
		filterChain.doFilter(request, response);
	}

}
