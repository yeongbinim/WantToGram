package sisibibi.wanttogram.feed.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;


@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final HttpSession session;
    private final MemberRepository memberRepository;

    public FeedEntity createFeed(FeedRequestDto request) {
        MemberEntity member = memberRepository.findByEmail((String) session.getAttribute("userEmail"))
                .orElseThrow(() -> new RuntimeException("User not found")); // 멤버를 불러왔다
        FeedEntity feed = new FeedEntity(null, member, request.getTitle(), request.getContent(), null, null);
        return feedRepository.save(feed);

    }

}

