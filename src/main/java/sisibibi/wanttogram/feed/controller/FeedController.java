package sisibibi.wanttogram.feed.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.feed.domain.FeedResponseDto;
import sisibibi.wanttogram.feed.domain.FeedRequestDto;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.service.FeedService;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    @PostMapping
    public ResponseEntity<FeedResponseDto> createFeed(@RequestBody FeedRequestDto request) {

        FeedEntity feed = feedService.createFeed(request);
        FeedResponseDto feedResponseDto = new FeedResponseDto(feed.getWriter().getName(), feed.getTitle(), feed.getContent(), feed.getCreatedAt(), feed.getUpdatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(feedResponseDto);
    }
}
