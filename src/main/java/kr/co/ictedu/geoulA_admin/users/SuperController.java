package kr.co.ictedu.geoulA_admin.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/super")
public class SuperController {

	@Autowired
	private UserInfoService userInfoService;

	// 회원 등급 토글: 유료(2) <-> 무료(1)
	@PutMapping("/user/{userId}/grade")
	public ResponseEntity<UserInfo> toggleUserGrade(@PathVariable("userId") Long userId) {
		UserInfo updated = userInfoService.toggleUserGrade(userId);
		return ResponseEntity.ok(updated);
	}

	// 회원 삭제
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId) {
		userInfoService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
}
