package kr.co.ictedu.geoulA_admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

}
