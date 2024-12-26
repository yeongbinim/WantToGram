package sisibibi.wanttogram.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.member.dto.MemberResponse;
import sisibibi.wanttogram.member.dto.MemberUpdate;
import sisibibi.wanttogram.member.dto.UpdateMemberPasswordRequest;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
		MemberEntity member = memberService.getMember(id);
		return ResponseEntity
			.ok()
			.body(MemberResponse.from(member));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MemberResponse> updateMember(
		@PathVariable Long id,
		@Valid @RequestBody MemberUpdate memberUpdate
	) {
		MemberEntity member = memberService.updateMember(id, memberUpdate);
		return ResponseEntity
			.ok()
			.body(MemberResponse.from(member));
	}

	// ::: 멤버 비밀번호 수정 API
	@PatchMapping("/{id}")
	public ResponseEntity<MemberResponse> updatePassword(
			@PathVariable Long id,
			@Valid @RequestBody UpdateMemberPasswordRequest request
	) {

		MemberEntity updatedMember = memberService.updateMemberPassword(id, request);

		MemberResponse memberResponse = new MemberResponse(
				updatedMember.getId(),
				updatedMember.getName(),
				updatedMember.getEmail(),
				updatedMember.getCreatedAt(),
				updatedMember.getUpdatedAt()
		);

		return new ResponseEntity<>(memberResponse, HttpStatus.OK);
	}
}
