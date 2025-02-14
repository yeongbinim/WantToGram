package sisibibi.wanttogram.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sisibibi.wanttogram.member.dto.MemberUpdate;
import sisibibi.wanttogram.member.dto.UpdateMemberPasswordRequest;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Setter
	@Column(name = "delete_at")
	private LocalDateTime deleteAt;

	public MemberEntity(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public MemberEntity update(MemberUpdate memberUpdate) {
		return new MemberEntity(
			id,
			memberUpdate.getUserName(),
			memberUpdate.getEmail(),
			password,
			createdAt,
			LocalDateTime.now(),
			deleteAt
		);
	}

	public void updatePassword(String password) {
		this.password = password;
	}
}
