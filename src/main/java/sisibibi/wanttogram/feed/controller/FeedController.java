package sisibibi.wanttogram.feed.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.service.FeedService;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

	private final FeedService feedService;

	@PostMapping // 피드 생성
	public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto request) {

		FeedEntity feed = feedService.createFeed(request);
		FeedResponseDto feedResponseDto = new FeedResponseDto(feed.getId(),
			feed.getWriter().getName(),
			feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());

		return ResponseEntity.status(HttpStatus.CREATED).body(feedResponseDto);
	}

	@GetMapping // 피드 페이징 조회
	public ResponseEntity<Page<FeedResponseDto>> findAllFeed(
		@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam(required = false) String startDate,
		@RequestParam(required = false) String endDate) {

		LocalDateTime start = startDate == null ?
			LocalDateTime.MIN : LocalDate.parse(startDate).atStartOfDay();
		LocalDateTime end = endDate == null ?
			LocalDateTime.MAX : LocalDate.parse(endDate).atTime(23, 59, 59);

		Page<FeedResponseDto> feeds = feedService.findAllFeed(start, end, pageable)
			.map(feedEntity ->
				new FeedResponseDto(
					feedEntity.getId(),
					feedEntity.getWriter().getName(),
					feedEntity.getTitle(),
					feedEntity.getContent(),
					feedEntity.getCreatedAt(),
					feedEntity.getUpdatedAt()
				)
			);

		return new ResponseEntity<>(feeds, HttpStatus.OK);
	}

	// 피드 단일 조회
	@GetMapping("/{id}")
	public ResponseEntity<FeedResponseDto> findFeedById(@PathVariable Long id) {
		FeedResponseDto feedResponseDto = feedService.findFeedById(id);
		return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
	}

	// 피드 수정
	@PutMapping("/{id}")
	public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long id,
		@RequestBody FeedRequestDto request) {
		FeedEntity feed = feedService.updateFeed(id, request);
		FeedResponseDto feedResponseDto = new FeedResponseDto(feed.getId(),
			feed.getWriter().getName(),
			feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());

		return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
	}

	// 피드 삭제
	@DeleteMapping("/{id}") // 피드 삭제
	public ResponseEntity<FeedResponseDto> deleteFeed(@PathVariable Long id) {
		feedService.deleteFeed(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
