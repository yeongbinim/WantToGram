package sisibibi.wanttogram.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.service.FeedService;

import java.util.List;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping // 피드 생성
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto request) {

        FeedEntity feed = feedService.createFeed(request);
        FeedResponseDto feedResponseDto = new FeedResponseDto(feed.getId(), feed.getWriter().getName(), feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(feedResponseDto);
    }

    @GetMapping // 피드 전체 조회 -> 페이징 조회 수정 예정
    public ResponseEntity<List<FeedResponseDto>> findAllFeed() {
        List<FeedResponseDto> feedResponseDtosList = feedService.findAllFeed();
        return new ResponseEntity<>(feedResponseDtosList, HttpStatus.OK);
    }

    // 피드 단일 조회
    @GetMapping("/{id}")
    public ResponseEntity<FeedResponseDto> findFeedById(@PathVariable Long id) {
        FeedResponseDto feedResponseDto = feedService.findFeedById(id);
        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    // 피드 수정
    @PutMapping("/{id}")
    public ResponseEntity<FeedResponseDto> updateFeed(@PathVariable Long id, @RequestBody FeedRequestDto request) {
        FeedEntity feed = feedService.updateFeed(id, request);
        FeedResponseDto feedResponseDto = new FeedResponseDto(feed.getId(), feed.getWriter().getName(), feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());

        return new ResponseEntity<>(feedResponseDto, HttpStatus.OK);
    }

    // 피드 삭제
    @DeleteMapping("/{id}") // 피드 삭제
    public ResponseEntity<FeedResponseDto> deleteFeed(@PathVariable Long id) {
        feedService.deleteFeed(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
