package kr.co.ictedu.geoulA_admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserInfoService {

	@Autowired
	private UserRepository userRepository;

	public Page<UserInfo> findbyuserinfo(int page, int size) {
		int startRow = (page - 1) * size + 1;
		int endRow = page * size;
		List<UserInfo> userList = userRepository.findbyuserinfo(startRow, endRow);
		int totaluser = userRepository.countByUser();
		return new PageImpl<>(userList, PageRequest.of(page - 1, size), totaluser);
	}

	public Page<UserInfo> countAllUsersSearch(String nickname, int page, int size) {
		int startRow = (page - 1) * size + 1;
		int endRow = page * size;
		List<UserInfo> userList = userRepository.getAllUsers(nickname, page, size);
		int totaluser = userRepository.countAllUsersSearch(nickname);
		return new PageImpl<>(userList, PageRequest.of(page - 1, size), totaluser);
	}

	public int countAllUsers() {
		return userRepository.countAllUsers();
	}

	public int todaysNewones() {
		return userRepository.todaysNewones();
	}

	public int getTotalPremium() {
		return userRepository.getTotalPremium();
	}

	// 회원 등급 토글: 유료(2) <-> 무료(1) (SUPERADMIN 전용)
	@Transactional
	public UserInfo toggleUserGrade(Long userId) {
		UserInfo user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
		int newGrade = user.getUser_grade_id() == 2 ? 1 : 2;
		userRepository.updateUserGrade(userId, newGrade);
		user.setUser_grade_id(newGrade);
		return user;
	}

	// 회원 삭제 (SUPERADMIN 전용)
	@Transactional
	public void deleteUser(Long userId) {
		if (!userRepository.existsById(userId)) {
			throw new RuntimeException("사용자를 찾을 수 없습니다: " + userId);
		}
		userRepository.deleteById(userId);
	}

}
