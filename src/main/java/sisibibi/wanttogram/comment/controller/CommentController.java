package sisibibi.wanttogram.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sisibibi.wanttogram.comment.dto.CommentResponse;
import sisibibi.wanttogram.comment.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds/{feed_id}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(
		@PathVariable("feed_id") Long feedId
	) {
		return null;
	}

	@GetMapping
	public ResponseEntity<List<CommentResponse>> getComments(
		@PathVariable("feed_id") Long feedId
	) {
		return null;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateComment(
		@PathVariable("feed_id") Long feedId,
		@PathVariable("id") Long commentId
	) {
		return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(
		@PathVariable("feed_id") Long feedId,
		@PathVariable("id") Long commentId
	) {
		return null;
	}
}
