package sisibibi.wanttogram.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sisibibi.wanttogram.comment.dto.CommentResponse;
import sisibibi.wanttogram.comment.dto.OneCommentResponse;
import sisibibi.wanttogram.comment.dto.UpdateCommentRequest;
import sisibibi.wanttogram.comment.entity.CommentEntity;
import sisibibi.wanttogram.comment.service.CommentService;
import sisibibi.wanttogram.common.PasswordEncoder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds/{feed_id}/comments")
public class CommentController {

	// 속성
	private final CommentService commentService;

	// 생성자

	// 기능
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

	// ::: 댓글 수정 API
	@PutMapping("/{id}")
	public ResponseEntity<OneCommentResponse> updateComment(
			@PathVariable("feed_id") Long feedId,
			@PathVariable("id") Long commentId,
			@RequestParam String password,
			@RequestBody UpdateCommentRequest request
	) {

		CommentEntity updateComment = commentService.updateComment(commentId, password, request);

		OneCommentResponse oneCommentResponse = new OneCommentResponse(
				updateComment.getId(),
				updateComment.getContent(),
				updateComment.getMember().getName(),
				updateComment.getCreatedAt(),
				updateComment.getUpdatedAt()
		);

		return new ResponseEntity<>(oneCommentResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteComment(
		@PathVariable("feed_id") Long feedId,
		@PathVariable("id") Long commentId,
		@RequestParam String password
	) {

		commentService.deleteComment(commentId, password);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
