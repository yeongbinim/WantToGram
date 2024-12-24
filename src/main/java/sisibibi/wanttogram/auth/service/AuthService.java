package sisibibi.wanttogram.auth.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.auth.domain.MemberCreate;
import sisibibi.wanttogram.auth.domain.MemberLogin;
import sisibibi.wanttogram.auth.domain.MemberResign;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.common.exception.DuplicateEmailException;
import sisibibi.wanttogram.common.exception.UnauthorizedException;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.time.LocalDateTime;
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
        MemberEntity member = memberRepository.findByEmail(memberLogin.getEmail())
                .filter(m -> m.getDeleteAt() == null)
                .filter(m -> passwordEncoder.matches(memberLogin.getPassword(), m.getPassword()))
                .orElseThrow(UnauthorizedException::new);

        session.setAttribute("userEmail", member.getEmail());
    }

    @Transactional
    public void resign(MemberResign memberResign) {
        MemberEntity member = memberRepository.findByEmail((String) session.getAttribute("userEmail"))
                .filter(m -> passwordEncoder.matches(memberResign.getPassword(), m.getPassword()))
                .orElseThrow(UnauthorizedException::new);

        member.setDeleteAt(LocalDateTime.now());
    }

}
