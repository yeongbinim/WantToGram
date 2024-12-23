package sisibibi.wanttogram.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.member.dto.MemberUpdate;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberEntity getMember(Long id) {
		// TODO Custom Exception 만들기
		return memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
	}

	public MemberEntity updateMember(Long id, MemberUpdate memberUpdate) {
		MemberEntity member = getMember(id);
		if (!passwordEncoder.matches(memberUpdate.getPassword(), member.getPassword())) {
			throw new IllegalArgumentException("패스워드가 잘못됨");
		}
		return memberRepository.save(member.update(memberUpdate));
	}
}
