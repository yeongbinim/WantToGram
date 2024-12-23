package sisibibi.wanttogram.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.common.exception.InvalidPasswordException;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.member.dto.MemberUpdate;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

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
}
