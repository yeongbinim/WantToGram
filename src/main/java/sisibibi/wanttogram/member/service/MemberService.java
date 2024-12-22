package sisibibi.wanttogram.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public MemberEntity getMember(Long id) {
		// TODO Custom Exception 만들기
		return memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("멤버를 찾을 수 없음"));
	}
}
