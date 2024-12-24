package sisibibi.wanttogram.feed.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final HttpSession session;
    private final MemberRepository memberRepository;


    public FeedEntity createFeed(FeedRequestDto request) {
        MemberEntity member = memberRepository.findByEmail((String) session.getAttribute("userEmail"))
                .orElseThrow(() -> new RuntimeException("User not found"));
        FeedEntity feed = new FeedEntity(null, member, request.getTitle(), request.getContent(), null, null);
        return feedRepository.save(feed);

    }

    // 피드 페이징 조회 (전제 조회 까지 구현)
    @Transactional(readOnly = true)
    public List<FeedResponseDto> findAllFeed() {
        List<FeedEntity> feedList = feedRepository.findAll();

        List<FeedResponseDto> dtoList = feedList.stream()
                .map(feedEntity -> new FeedResponseDto(feedEntity.getId(), feedEntity.getWriter().getName(), feedEntity.getTitle(), feedEntity.getContent(), feedEntity.getCreatedAt(), feedEntity.getUpdatedAt()))
                .toList();
        return dtoList;
    }

    // 피드 단일 조회
    public FeedResponseDto findFeedById(Long id) {
        FeedEntity feed = feedRepository.findFeedByIdOrElseThrowById(id);
        return new FeedResponseDto(feed.getId(), feed.getWriter().getName(), feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());
    }


    // 피드 업데이트
    public FeedEntity updateFeed(Long id, FeedRequestDto request) {
        FeedEntity feed = feedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feed not found"));
        feed.updateFeedDto(request);
        return feed;
    }

    // 피드 삭제
    public void deleteFeed(Long id) {
        feedRepository.deleteById(id);
    }
}




