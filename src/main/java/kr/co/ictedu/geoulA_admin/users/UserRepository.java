package kr.co.ictedu.geoulA_admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//select user_id,email,nickname,gender,udate FROM USERS WHERE user_id = 4;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface UserRepository extends JpaRepository<UserInfo,Long>{
	
	// 대시보드 메인 화면(최근 가입 유저 표시)
	@Query(value = "select * from (select user_id,user_grade_id,email,nickname,skin_type,gender,udate,ROW_NUMBER() OVER (ORDER BY u.udate desc) as row_num from users u where udate >= sysdate - 7)" +
			"where row_num between :startRow and :endRow"
			,nativeQuery = true)
	List<UserInfo> findbyuserinfo(@Param("startRow") int startRow,@Param("endRow") int endRow);
	
	@Query(value = "select count(*) from users u where u.udate > sysdate - 7",nativeQuery = true)
	int countByUser();
	
	// 회원 관리 페이지(모든 유저 표시 + 닉네임 검색)
	@Query(value = "SELECT * FROM (SELECT user_id,user_grade_id,email,nickname,skin_type,gender,udate," +
	"ROW_NUMBER() OVER (ORDER BY u.udate DESC) AS row_num FROM users u WHERE u.nickname LIKE %:nickname% OR u.email LIKE %:nickname%)" +
	"WHERE row_num between :startRow AND :endRow", nativeQuery = true)
	List<UserInfo> getAllUsers(@Param("nickname") String nickname, @Param("startRow") int startRow,@Param("endRow") int endRow);
	
	@Query(value = "SELECT COUNT(*) FROM users u WHERE u.nickname LIKE %:nickname% OR u.email LIKE %:nickname%", nativeQuery = true)
	int countAllUsersSearch(@Param("nickname") String nickname);

	// 총 회원
	@Query(value = "SELECT COUNT(*) FROM users", nativeQuery = true)
	int countAllUsers();
	
	// 오늘 가입한 회원 수
	@Query(value = "SELECT COUNT(*) FROM users u WHERE TRUNC(u.udate) = TRUNC(SYSDATE)", nativeQuery = true)
	int todaysNewones();

	// 유료 회원 수
	@Query(value = "SELECT COUNT(*) FROM users u WHERE u.user_grade_id = 2", nativeQuery = true)
	int getTotalPremium();
}
