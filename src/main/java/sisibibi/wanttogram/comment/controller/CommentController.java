package sisibibi.wanttogram.comment.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import sisibibi.wanttogram.comment.dto.CommentCreateRequest;
import sisibibi.wanttogram.comment.dto.CommentResponse;
import sisibibi.wanttogram.comment.dto.OneCommentResponse;
import sisibibi.wanttogram.comment.dto.UpdateCommentRequest;
import sisibibi.wanttogram.comment.entity.CommentEntity;
import sisibibi.wanttogram.comment.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/feeds/{feed_id}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(
		@SessionAttribute String userEmail,
		@PathVariable("feed_id") Long feedId,
		@RequestParam(required = false) Long parentId,
		@Valid @RequestBody CommentCreateRequest commentCreate
	) {
		CommentEntity comment = commentService.createComment(
			userEmail,
			feedId,
			commentCreate,
			parentId);
		return ResponseEntity
			.created(URI.create("/feeds/" + feedId + "/comments/" + comment.getId()))
			.build();
	}

	@GetMapping
	public ResponseEntity<List<CommentResponse>> getComments(
		@PathVariable("feed_id") Long feedId
	) {
		List<CommentResponse> comments = commentService.getAllComments(feedId);
		return ResponseEntity.ok(comments);
	}

	// ::: 댓글 수정 API
	@PutMapping("/{id}")
	public ResponseEntity<OneCommentResponse> updateComment(
		@PathVariable("id") Long commentId,
		@RequestBody UpdateCommentRequest request
	) {

		CommentEntity updateComment = commentService.updateComment(commentId, request);

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
		@PathVariable("id") Long commentId,
		@RequestBody UpdateCommentRequest request// {"password":"현재비밀번호" *DTO에는 2개의 필드만 있지만, password만 입력!
	) {

		commentService.deleteComment(commentId, request.getPassword());

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
