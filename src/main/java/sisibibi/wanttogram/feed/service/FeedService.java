package sisibibi.wanttogram.feed.service;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.common.exception.UnauthorizedException;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class FeedService {

	private final FeedRepository feedRepository;
	private final HttpSession session;
	private final MemberRepository memberRepository;

  private void ownerCheck(FeedEntity feed,String message) {
    String userEmail = (String) session.getAttribute("userEmail");
    MemberEntity member = memberRepository.findByEmail(userEmail)
            .orElseThrow(() -> new NotFoundException("멤버", userEmail));
    if (!feed.getWriter().getEmail().equals(member.getEmail())) {
        throw new UnauthorizedException(message);
    }
  }

	public FeedEntity createFeed(FeedRequestDto request) {
		MemberEntity member = memberRepository.findByEmail(
				(String) session.getAttribute("userEmail"))
			.orElseThrow(() -> new RuntimeException("User not found"));
		FeedEntity feed = new FeedEntity(null, member, request.getTitle(), request.getContent(),
			null, null);
		return feedRepository.save(feed);

	}
 

	// 피드 페이징 조회
	@Transactional(readOnly = true)
	public Page<FeedEntity> findAllFeed(LocalDateTime start, LocalDateTime end, Pageable pageable) {
		MemberEntity member = memberRepository.findByEmail(
			(String) session.getAttribute("userEmail")).orElse(null);

		return member == null ?
			feedRepository.findAllOrderedByLikes(start, end, pageable)
			: feedRepository.findAllByFollowingOrderedByLikes(member.getId(), start, end, pageable);
	}

	// 피드 단일 조회
	public FeedResponseDto findFeedById(Long id) {
		FeedEntity feed = feedRepository.findFeedByIdOrElseThrowById(id);
		return new FeedResponseDto(feed.getId(), feed.getWriter().getName(), feed.getTitle(),
			feed.getContent(),
			feed.getCreatedAt(), feed.getUpdatedAt());
	}


	// 피드 업데이트
  public FeedEntity updateFeed(Long id, FeedRequestDto request) {
    FeedEntity feed = feedRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Feed not found"));
    ownerCheck(feed,"본인의 피드만 수정 가능합니다.");
    feed.updateFeedDto(request);
    return feed;
  }

  // 피드 삭제
  public void deleteFeed(Long id) {
    FeedEntity feed = feedRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Feed not found"));
    ownerCheck(feed,"본인의 피드만 삭제 가능합니다.");
    feedRepository.delete(feed);
  }

	// 앱 실행시 기본값 세팅
	@PostConstruct
	private void initFeeds() {
		List<MemberEntity> members = memberRepository.findAll();
		if (members.isEmpty()) {
			// 멤버가 없으면 멤버를 생성
			MemberEntity member = new MemberEntity("User", "user@example.com", "password@1");
			members.add(memberRepository.save(member));
		}
		List<FeedEntity> feeds = feedRepository.findAll();
		if (feeds.isEmpty()) {
			// 피드가 없으면 피드 24개를 생성
			for (int i = 0; i < 24; i++) {
				FeedEntity feed = new FeedEntity(null, members.get(0), "제목", "내용", null, null);
				feedRepository.save(feed);
			}
		}
	}
}
