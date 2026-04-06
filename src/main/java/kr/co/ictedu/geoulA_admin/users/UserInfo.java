package kr.co.ictedu.geoulA_admin.users;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@SequenceGenerator(name = "seq_users",sequenceName = "seq_users",allocationSize = 1)
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_users")
	private Long user_id;
	
	private String email;
	
	private String nickname;
	
	private String gender;
	
	private String udate;
}
