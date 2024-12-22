package sisibibi.wanttogram.member.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sisibibi.wanttogram.member.entity.MemberEntity;

@Getter
@AllArgsConstructor
public class MemberResponse {

	private Long id;
	private String name;
	private String email;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static MemberResponse from(MemberEntity member) {
		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}
}
