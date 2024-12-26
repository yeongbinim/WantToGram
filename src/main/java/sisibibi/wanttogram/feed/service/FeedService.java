package sisibibi.wanttogram.feed.service;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // 피드 페이징 조회
    @Transactional(readOnly = true)
    public Page<FeedResponseDto> findAllFeed(String startDate, String endDate, Pageable pageable) {
        LocalDateTime start = (startDate == null) ? LocalDateTime.MIN : LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime end = (endDate == null) ? LocalDateTime.MAX : LocalDate.parse(endDate).atTime(23, 59, 59);

        Page<FeedEntity> feedList = null;
        MemberEntity member = memberRepository.findByEmail((String) session.getAttribute("userEmail"))
                .orElse(null);

        if (member!= null) {
            feedList = feedRepository.findAllFeedsByFollowing(member.getId(), pageable);
            System.out.println(feedList.stream().count());
        } else {
            feedList = feedRepository.findAllByUpdatedAtBetween(start, end, pageable);

        }

        Page<FeedResponseDto> dtoList = feedList
                .map(feedEntity -> new FeedResponseDto(feedEntity.getId(), feedEntity.getWriter().getName(),
                        feedEntity.getTitle(), feedEntity.getContent(), feedEntity.getCreatedAt(),
                        feedEntity.getUpdatedAt()));
        return dtoList;
    }

    // 피드 단일 조회
    public FeedResponseDto findFeedById(Long id) {
        FeedEntity feed = feedRepository.findFeedByIdOrElseThrowById(id);
        return new FeedResponseDto(feed.getId(), feed.getWriter().getName(), feed.getTitle(), feed.getContent(),
                feed.getCreatedAt(), feed.getUpdatedAt());
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




