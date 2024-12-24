package sisibibi.wanttogram.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.like.service.LikeService;

@RestController
@RequestMapping("feeds")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @GetMapping("/{id}/likes")
    public ResponseEntity<Void> feedLike (@PathVariable long feedId){
        likeService.feedlike(feedId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}/likes")
    public ResponseEntity<Void> feedUnLike (@PathVariable long feedId){
        likeService.feedUnlike(feedId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
