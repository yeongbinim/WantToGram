package sisibibi.wanttogram.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.member.dto.MemberResponse;
import sisibibi.wanttogram.member.dto.MemberUpdate;
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
}
