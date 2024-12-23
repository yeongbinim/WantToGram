package sisibibi.wanttogram.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.auth.domain.MemberCreate;
import sisibibi.wanttogram.auth.domain.MemberLogin;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.common.exception.DuplicateEmailException;
import sisibibi.wanttogram.common.exception.UnauthorizedException;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

    public void register(MemberCreate memberCreate) {
        //이메일 중복체크
        if (memberRepository.existsByEmail(memberCreate.getEmail())) {
            throw new DuplicateEmailException();
        }
        MemberEntity member = new MemberEntity(
                memberCreate.getUserName(), memberCreate.getEmail(),
                passwordEncoder.encode(memberCreate.getPassword())
        );
        memberRepository.save(member);
    }

    public void login(MemberLogin memberLogin) {
        Optional<MemberEntity> optionalMember = memberRepository.findByEmail(memberLogin.getEmail());
        if (optionalMember.isPresent()) {
            MemberEntity member = optionalMember.get();
            if (passwordEncoder.matches(memberLogin.getPassword(), member.getPassword())) {
                session.setAttribute("userEmail", member.getEmail());
                return;
            }
        }
        throw new UnauthorizedException();
    }
}
