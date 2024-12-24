package sisibibi.wanttogram.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sisibibi.wanttogram.comment.dto.CommentCreateRequest;
import sisibibi.wanttogram.comment.dto.CommentResponse;
import sisibibi.wanttogram.comment.dto.UpdateCommentRequest;
import sisibibi.wanttogram.comment.entity.CommentEntity;
import sisibibi.wanttogram.comment.repository.CommentRepository;
import sisibibi.wanttogram.common.PasswordEncoder;
import sisibibi.wanttogram.common.exception.NotFoundException;
import sisibibi.wanttogram.feed.entity.FeedEntity;
import sisibibi.wanttogram.feed.repository.FeedRepository;
import sisibibi.wanttogram.member.entity.MemberEntity;
import sisibibi.wanttogram.member.infrastructure.MemberRepository;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final FeedRepository feedRepository;
	private final PasswordEncoder passwordEncoder;

	public CommentEntity createComment(
		String userEmail,
		Long feedId,
		CommentCreateRequest commentCreate,
		Long parentId
	) {
		MemberEntity member = memberRepository.findByEmail(userEmail)
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없다"));
		FeedEntity feed = feedRepository.findById(feedId)
			.orElseThrow(() -> new NotFoundException("피드", feedId));
		CommentEntity parent = parentId == null ? null : commentRepository.findById(parentId)
			.orElseThrow(() -> new NotFoundException("댓글", parentId));
		CommentEntity commentEntity = new CommentEntity(
			member,
			feed,
			parent,
			commentCreate.getContent()
		);
		return commentRepository.save(commentEntity);
	}

	@Transactional(readOnly = true)
	public List<CommentResponse> getAllComments(Long feedId) {
		List<CommentEntity> comments = commentRepository.findByFeedIdOrderByCreatedAtAsc(feedId);
		return CommentResponse.from(comments);
	}

	// 댓글 수정 :: TODO 사용자 찾아서 일치해야 댓글 수정 가능
	public CommentEntity updateComment(Long id, String password, UpdateCommentRequest request) {

		CommentEntity foundComment = commentRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("comment", id));

		if (passwordEncoder.matches(password, foundComment.getMember().getPassword())) {
			foundComment.updateComment(request.getContent());
		}

		return foundComment;
	}

	// 댓글 삭제 :: TODO 사용자 찾아서 일치해야 댓글 삭제 가능
	public void deleteComment(Long id, String password) {

		CommentEntity foundComment = commentRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("comment", id));

		if (passwordEncoder.matches(password, foundComment.getMember().getPassword())) {
			commentRepository.delete(foundComment);
		}
	}

}
