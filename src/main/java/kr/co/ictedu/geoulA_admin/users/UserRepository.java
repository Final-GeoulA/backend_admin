package kr.co.ictedu.geoulA_admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//select user_id,email,nickname,gender,udate FROM USERS WHERE user_id = 4;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface UserRepository extends JpaRepository<UserInfo,Long>{
	
	@Query(value = "select * from (select user_id,email,nickname,gender,udate,ROW_NUMBER() OVER (ORDER BY u.udate desc) as row_num from users u where udate >= sysdate - 7)"
			+ "where row_num between :startRow and :endRow"
			,nativeQuery = true)
	List<UserInfo> findbyuserinfo(@Param("startRow") int startRow,@Param("endRow") int endRow);
	
	@Query(value = "select count(*) from users u where u.udate > sysdate - 7",nativeQuery = true)
	int countByUser();
	
	
}
