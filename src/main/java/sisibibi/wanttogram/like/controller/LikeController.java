package sisibibi.wanttogram.like.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.like.service.LikeService;

@RestController
@RequestMapping("feeds")
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/{feedId}/likes")
	public ResponseEntity<Void> feedLike(@PathVariable Long feedId) {
		likeService.feedlike(feedId);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{feedId}/likes")
	public ResponseEntity<Void> feedUnLike(@PathVariable Long feedId) {
		likeService.feedUnlike(feedId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PostMapping("/comments/{comments_id}/likes")
	public ResponseEntity<Void> commentLike(@PathVariable Long comments_id) {
		likeService.commentLike(comments_id);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/comments/{comments_id}/likes")
	public ResponseEntity<Void> commentUnLike(@PathVariable Long comments_id) {
		likeService.commentUnLike(comments_id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
