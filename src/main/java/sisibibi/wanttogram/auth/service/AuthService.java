package sisibibi.wanttogram.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.auth.domain.MemberCreate;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    public void register(MemberCreate memberCreate) {
        //이메일 중복체크
        if(memberRepository.existsByEmail(memberCreate.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 존재하는 회원이 Email입니다.");
        }
        MemberEntity member = new MemberEntity(
                memberCreate.getUserName(), memberCreate.getEmail(),
                passwordEncoder.encode(memberCreate.getPassword())
        );
        memberRepository.save(member);
    }
}
