package kr.co.ictedu.geoulA_admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserInfoController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	@PostMapping("/info")
	public Page<UserInfo> findbyuserinfo(@RequestParam(name = "page",defaultValue = "1") int page,
			@RequestParam(name = "size",defaultValue = "5") int size){
		
		return userInfoService.findbyuserinfo(page, size);
	}
	
}
