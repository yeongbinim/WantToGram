package sisibibi.wanttogram.member.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.common.exception.InvalidPasswordException;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.common.exception.SamePasswordException;
import sisibibi.wanttogram.member.dto.MemberUpdate;
import sisibibi.wanttogram.member.dto.UpdateMemberPasswordRequest;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final HttpSession httpSession;

	public MemberEntity getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("멤버", id));
	}

	public MemberEntity updateMember(Long id, MemberUpdate memberUpdate) {
		MemberEntity member = getMember(id);
		if (!passwordEncoder.matches(memberUpdate.getPassword(), member.getPassword())) {
			throw new InvalidPasswordException();
		}
		return memberRepository.save(member.update(memberUpdate));
	}

	// 멤버 비밀번호 수정
	@Transactional
	public MemberEntity updateMemberPassword(Long id, UpdateMemberPasswordRequest request) {

		MemberEntity member = getLoggedInMember();

		if (member.getId().equals(id)) {
			if (passwordEncoder.matches(request.getPassword(), member.getPassword())) {
				if (passwordEncoder.matches(request.getUpdatePassword(), member.getPassword())) {
					throw new SamePasswordException();
				}
				member.updatePassword(passwordEncoder.encode(request.getUpdatePassword()));
			} else {
				throw new InvalidPasswordException();
			}
		}

		return member;
	}

	// 로그인한 멤버 가져오기
	private MemberEntity getLoggedInMember() {
		String userEmail = (String) httpSession.getAttribute("userEmail");
		return memberRepository.findByEmail(userEmail)
				.orElseThrow(() -> new NotFoundException("멤버", userEmail));
	}
}
